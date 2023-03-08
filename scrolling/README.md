# Scrolling

Common Scrolling Jar File For NDM API's.

This project contains common API scrolling functions for Quarkus based
applications and it provides a common scrolling format for all API responses
that may be generated in underlying code.

If you want to learn about Quarkus, please visit this [website](
https://quarkus.io).

## Maven dependency

```xml
<dependency>
    <groupId>com.postnord.ndm.base</groupId>
    <artifactId>scrolling</artifactId>
    <version>add version</version>
</dependency>
```

The staging build generates the version.

## Usage

### client side

The purpose of Scrollable, described below, is to supply a _*next*_ link,
alongside the retrieved items, to the API user. This way the API user issuing
one request can pick up the cursor value for the next request from the _*next*_
field of the response.

A first API request could look like, e.g:

```java
https://domain/ndm/api/someservice?sort=eventAt=desc
```

The subsequent request would then look like, assuming there is more data:

```java
https://domain/ndm/api/someservice?cursor=P3NvcnQ9ZXZlbnRBdD1kZXNjJmN1cnNvcj02JnBhZ2Vfc2l6ZT0y
```

The cursor data here is a base64 representation of the request parameter data,
including all the updated data needed to retieve the next page.

```java
response: {
    "items",
    "next"
}
```

The `https://domain/ndm/api/someservice` part is not included in the next/cursor
string.

### server side

To use the Scrollable, build a ScrollableResponse with a Scrollable containing
your data items, your current page and limit, also add the raw query parameters.

Since the Scrollable can't by itself know if the end has been reached, the user
has to supply either a totalCount _*or*_ a hasNextPage as well.

- items(List\<String\>): the items retrieved
- totalCount(int): the total count of the items stored in the backend.
- hasNextPage(boolean): the user knows, by some means, if there are pages left.
- rawQueryParams(String): everything after the '?' in the request being
  processed, e.g. "sort=eventAt=desc"
- page(int): current page number
- limit(int): number of items per page

```java
Response response = ScrollableResponse.builder()
            .scrollable(Scrollable.builder()
                .items(items)
                .page(5).limit(2)
                .rawQueryParams("sort=eventAt=desc")
                .totalCount(120)
                .build())
            .build())
```

or

```java
Response response = ScrollableResponse.builder()
            .scrollable(Scrollable.builder()
                .items(items)
                .page(5).limit(2)
                .rawQueryParams("sort=eventAt=desc")
                .hasNextPage(true)
                .build())
            .build())
```

```java
final Scrollable scrollableParsedFromResponse = response.readEntity(Scrollable.class);
```

From the response, data about the response can be retrieved:

```java
response.getMediaType())
response.getStatus()
...
```

The Scrollable, items and scrolling data can be retrieved

```java
scrollableParsedFromResponse.getNextLinkDecoded() -> "sort=eventAt=desc&cursor=6&limit=2"
scrollableParsedFromResponse.getItems() -> List of items
```

### Scroller Helpers

There are a few Scroller classes that can assist in creating objects to use
when making scrollable requests. A typical usage could be like the code example
below.

Create a _*ScrollableQuery*_, which creates an object, taking into account
that the cursor can be a base64 string or a page number. Use the elements of
the ScrollableQuery object in your repository query.
If the repository query is executed asking for one extra element,
the ScrollerHelper will automatically know if there are more pages and set
the _*next*_ element in the response accordingly.
After this, the `ScrollerHelper.getScrollableResponse` can be used to
create a Response structure to be returned to the client.

The _*createdAt*_ is a timestamp set by the ScrollerQuery.
This should be used to make sure no new (since first request) elements are
included in a subsequent call using the cursor from the request, it limits
the repository search to only include entries older than _*createdAt*_,
which is the time of the first request.

#### Example usage of Scroller

<!-- markdownlint-disable MD013 -->
```java
@GET
@Produces({"application/json", "application/problem+json"})
public Response listSomething(
        @QueryParam("q") @NdmStringLength(length = ConstantsHelper.RSQL_FILTER_MAX_LENGTH, nullAllowed = true) final String q,
        @QueryParam("cursor") @NdmString(nullAllowed = true) final String cursor,
        @QueryParam("limit") @NdmPositiveNumber(nullAllowed = true) @Max(ConstantsHelper.MAX_PAGE_SIZE) @DefaultValue("100") final Integer limit,
        @QueryParam("sort") @NdmStringRegex(regex = "(\\+|-)(id|status|name|type)", nullAllowed = true) @DefaultValue("+id") final String sort) {

       ...
        final ScrollableQuery query = new ScrollableQuery(q, cursor, limit, sort);

        final List<SomeListV1> listItems = someRepository.list(
            query.getQ(),
            query.getPageNumber() * query.getLimit(),
            query.getLimit() + 1,
            query.getSort(),
            query.getCreatedAt()
        ).stream().map(v1MapperService::toSomeListV1).collect(Collectors.toList());

        return ScrollerHelper.getScrollableResponse(listItems, query);
}
```
<!-- markdownlint-enable MD013 -->
