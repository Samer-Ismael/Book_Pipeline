# OpenAPI definition

> Version v0

## Path Table

| Method | Path | Description |
| --- | --- | --- |
| PUT | [/users/{id}](#putusersid) | This is for updating a user, for admins only. Updates only the fields that are set.
eg. {"role": "ROLE_ADMIN"} to set user with id to admin. |
| DELETE | [/users/{id}](#deleteusersid) | This is for deleting a user, for admins only |
| PUT | [/users/changePass](#putuserschangepass) | This is for changing the password of the current user (Change your password), for users and admins |
| GET | [/books](#getbooks) | Get all books |
| PUT | [/books](#putbooks) | Replace book with new book by id |
| POST | [/books](#postbooks) | Save book |
| GET | [/authors](#getauthors) | Get all authors |
| PUT | [/authors](#putauthors) | Replace author with new author by id |
| POST | [/authors](#postauthors) | Save author |
| POST | [/auth/register](#postauthregister) | This is for registering a new user |
| POST | [/auth/login](#postauthlogin) | This is for logging in a user and getting a token for the user. |
| GET | [/users/{name}](#getusersname) | This is for getting a user by name, for users and admins |
| GET | [/users/all](#getusersall) | This is for getting all users, for users and admins |
| GET | [/books/{id}](#getbooksid) | Get book by id |
| DELETE | [/books/{id}](#deletebooksid) | Delete book by id |
| GET | [/authors/{id}](#getauthorsid) | Get author by id |
| DELETE | [/authors/{id}](#deleteauthorsid) | Delete author by id |
| GET | [/authors/{id}/books](#getauthorsidbooks) | Get all books by author id |
| DELETE | [/users/me](#deleteusersme) | This is for deleting the current user (Deleting you account), for users and admins |

## Reference Table

| Name | Path | Description |
| --- | --- | --- |
| UserEntity | [#/components/schemas/UserEntity](#componentsschemasuserentity) |  |
| ChangingPassword | [#/components/schemas/ChangingPassword](#componentsschemaschangingpassword) |  |
| Author | [#/components/schemas/Author](#componentsschemasauthor) |  |
| Book | [#/components/schemas/Book](#componentsschemasbook) |  |
| AuthRequest | [#/components/schemas/AuthRequest](#componentsschemasauthrequest) |  |
| DeleteResponse | [#/components/schemas/DeleteResponse](#componentsschemasdeleteresponse) |  |

## Path Details

***

### [PUT]/users/{id}

- Summary  
  This is for updating a user, for admins only. Updates only the fields that are set.  
  eg. {"role": "ROLE_ADMIN"} to set user with id to admin.

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

- Summary  
  This is for deleting a user, for admins only

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

- Summary  
  This is for changing the password of the current user (Change your password), for users and admins

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

- Summary  
  Get all books

#### Responses

- 200 OK

`*/*`

```ts
{
  id?: integer
  title?: string
  author: {
    id?: integer
    name?: string
  }
}[]
```

***

### [PUT]/books

- Summary  
  Replace book with new book by id

#### RequestBody

- application/json

```ts
{
  id?: integer
  title?: string
  author: {
    id?: integer
    name?: string
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
    id?: integer
    name?: string
  }
}
```

***

### [POST]/books

- Summary  
  Save book

#### RequestBody

- application/json

```ts
{
  id?: integer
  title?: string
  author: {
    id?: integer
    name?: string
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
    id?: integer
    name?: string
  }
}
```

***

### [GET]/authors

- Summary  
  Get all authors

#### Responses

- 200 OK

`*/*`

```ts
{
  id?: integer
  name?: string
}[]
```

***

### [PUT]/authors

- Summary  
  Replace author with new author by id

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
  id?: integer
  name?: string
}
```

***

### [POST]/authors

- Summary  
  Save author

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
  id?: integer
  name?: string
}
```

***

### [POST]/auth/register

- Summary  
  This is for registering a new user

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

- Summary  
  This is for logging in a user and getting a token for the user.

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

- Summary  
  This is for getting a user by name, for users and admins

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

- Summary  
  This is for getting all users, for users and admins

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

- Summary  
  Get book by id

#### Responses

- 200 OK

`*/*`

```ts
{
  id?: integer
  title?: string
  author: {
    id?: integer
    name?: string
  }
}
```

***

### [DELETE]/books/{id}

- Summary  
  Delete book by id

#### Responses

- 200 OK

`*/*`

```ts
{
  message?: enum[SUCCESS, FAILURE]
}
```

***

### [GET]/authors/{id}

- Summary  
  Get author by id

#### Responses

- 200 OK

`*/*`

```ts
{
  id?: integer
  name?: string
}
```

***

### [DELETE]/authors/{id}

- Summary  
  Delete author by id

#### Responses

- 200 OK

`*/*`

```ts
{
  message?: enum[SUCCESS, FAILURE]
}
```

***

### [GET]/authors/{id}/books

- Summary  
  Get all books by author id

#### Responses

- 200 OK

`*/*`

```ts
{
  id?: integer
  title?: string
  author: {
    id?: integer
    name?: string
  }
}[]
```

***

### [DELETE]/users/me

- Summary  
  This is for deleting the current user (Deleting you account), for users and admins

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
  id?: integer
  name?: string
}
```

### #/components/schemas/Book

```ts
{
  id?: integer
  title?: string
  author: {
    id?: integer
    name?: string
  }
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