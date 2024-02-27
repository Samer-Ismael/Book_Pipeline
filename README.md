# Book_Pipeline

# OpenAPI definition

> Version v0

## Path Table

| Method | Path | Description |
| --- | --- | --- |
| PUT | [/users/{id}](#putusersid) |  |
| DELETE | [/users/{id}](#deleteusersid) |  |
| PUT | [/users/changePass](#putuserschangepass) |  |
| GET | [/books](#getbooks) |  |
| PUT | [/books](#putbooks) |  |
| POST | [/books](#postbooks) |  |
| GET | [/authors](#getauthors) |  |
| PUT | [/authors](#putauthors) |  |
| POST | [/authors](#postauthors) |  |
| POST | [/auth/register](#postauthregister) |  |
| POST | [/auth/login](#postauthlogin) |  |
| GET | [/users/{name}](#getusersname) |  |
| GET | [/users/all](#getusersall) |  |
| GET | [/books/{id}](#getbooksid) |  |
| DELETE | [/books/{id}](#deletebooksid) |  |
| GET | [/authors/{id}](#getauthorsid) |  |
| DELETE | [/authors/{id}](#deleteauthorsid) |  |
| DELETE | [/users/me](#deleteusersme) |  |

## Reference Table

| Name | Path | Description |
| --- | --- | --- |
| UserEntity | [#/components/schemas/UserEntity](#componentsschemasuserentity) |  |
| ChangingPassword | [#/components/schemas/ChangingPassword](#componentsschemaschangingpassword) |  |
| Author | [#/components/schemas/Author](#componentsschemasauthor) |  |
| Book | [#/components/schemas/Book](#componentsschemasbook) |  |
| AuthorPutRequest | [#/components/schemas/AuthorPutRequest](#componentsschemasauthorputrequest) |  |
| AuthRequest | [#/components/schemas/AuthRequest](#componentsschemasauthrequest) |  |
| DeleteResponse | [#/components/schemas/DeleteResponse](#componentsschemasdeleteresponse) |  |

## Path Details

***

### [PUT]/users/{id}

#### RequestBody

- application/json

```ts
{
  id?: integer
  username?: string
  password?: string
  role?: enum[ROLE_USER, ROLE_ADMIN]
}
```

#### Responses

- 200 OK

`*/*`

```ts
{
  "type": "string"
}
```

***

### [DELETE]/users/{id}

#### Responses

- 200 OK

`*/*`

```ts
{
  id?: integer
  username?: string
  password?: string
  role?: enum[ROLE_USER, ROLE_ADMIN]
}
```

***

### [PUT]/users/changePass

#### RequestBody

- application/json

```ts
{
  oldPassword?: string
  newPassword?: string
  confirmPassword?: string
}
```

#### Responses

- 200 OK

`*/*`

```ts
{
  "type": "string"
}
```

***

### [GET]/books

#### Responses

- 200 OK

`*/*`

```ts
{
  id?: integer
  title?: string
  author: {
  }
}[]
```

***

### [PUT]/books

#### RequestBody

- application/json

```ts
{
  id?: integer
  title?: string
  author: {
  }
}
```

#### Responses

- 200 OK

`*/*`

```ts
{
  id?: integer
  title?: string
  author: {
  }
}
```

***

### [POST]/books

#### RequestBody

- application/json

```ts
{
  id?: integer
  title?: string
  author: {
  }
}
```

#### Responses

- 200 OK

`*/*`

```ts
{
  "type": "string"
}
```

***

### [GET]/authors

#### Responses

- 200 OK

`*/*`

```ts
{
}[]
```

***

### [PUT]/authors

#### RequestBody

- application/json

```ts
{
  id?: integer
  name?: string
}
```

#### Responses

- 200 OK

`*/*`

```ts
{
}
```

***

### [POST]/authors

#### RequestBody

- application/json

```ts
{
}
```

#### Responses

- 200 OK

`*/*`

```ts
{
}
```

***

### [POST]/auth/register

#### RequestBody

- application/json

```ts
{
  username?: string
  password?: string
}
```

#### Responses

- 200 OK

`*/*`

```ts
{
  "type": "string"
}
```

***

### [POST]/auth/login

#### RequestBody

- application/json

```ts
{
  username?: string
  password?: string
}
```

#### Responses

- 200 OK

`*/*`

```ts
{
  "type": "string"
}
```

***

### [GET]/users/{name}

#### Responses

- 200 OK

`*/*`

```ts
{
  id?: integer
  username?: string
  password?: string
  role?: enum[ROLE_USER, ROLE_ADMIN]
}
```

***

### [GET]/users/all

#### Responses

- 200 OK

`*/*`

```ts
{
  id?: integer
  username?: string
  password?: string
  role?: enum[ROLE_USER, ROLE_ADMIN]
}[]
```

***

### [GET]/books/{id}

#### Responses

- 200 OK

`*/*`

```ts
{
  id?: integer
  title?: string
  author: {
  }
}
```

***

### [DELETE]/books/{id}

#### Responses

- 200 OK

`*/*`

```ts
{
  "type": "string"
}
```

***

### [GET]/authors/{id}

#### Responses

- 200 OK

`*/*`

```ts
{
}
```

***

### [DELETE]/authors/{id}

#### Responses

- 200 OK

`*/*`

```ts
{
  message?: enum[SUCCESS, FAILURE]
}
```

***

### [DELETE]/users/me

#### Responses

- 200 OK

`*/*`

```ts
{
  "type": "string"
}
```

## References

### #/components/schemas/UserEntity

```ts
{
  id?: integer
  username?: string
  password?: string
  role?: enum[ROLE_USER, ROLE_ADMIN]
}
```

### #/components/schemas/ChangingPassword

```ts
{
  oldPassword?: string
  newPassword?: string
  confirmPassword?: string
}
```

### #/components/schemas/Author

```ts
{
}
```

### #/components/schemas/Book

```ts
{
  id?: integer
  title?: string
  author: {
  }
}
```

### #/components/schemas/AuthorPutRequest

```ts
{
  id?: integer
  name?: string
}
```

### #/components/schemas/AuthRequest

```ts
{
  username?: string
  password?: string
}
```

### #/components/schemas/DeleteResponse

```ts
{
  message?: enum[SUCCESS, FAILURE]
}
```
