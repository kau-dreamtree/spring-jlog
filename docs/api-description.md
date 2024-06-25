# API 명세

## Room

- path: `/api/room`

### 방 생성

#### request

```http request
POST /api/room
Content-Type: application/json

{
  "username": "zeus"
}
```

#### response

```http request
HTTP/1.1 201 
Content-Type: application/json

{
  "room_code": "60406dcb"
}
```

### 방 참여

#### request

```http request
PUT /api/room

{
  "room_code": "60406dcb",
  "username": "lizzy"
}
```

#### response

```http request
HTTP/1.1 200 
```

## Log

- path: `/api/log`

### 로그 생성

#### request

```http request
POST /api/log
Content-Type: application/json

{
  "room_code": "c0b2462c",
  "username": "lizzy",
  "amount": 100000,
  "memo": "Hello world!"
}
```

#### response

```http request
HTTP/1.1 201
```

### 로그 조회

#### request

```http request
GET /api/log?room_code=c0b2462c&username=zeus
```

#### response

```http request
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 21 Jun 2024 06:50:11 GMT

{
  "logs": [
    {
      "username": "lizzy",
      "memo": "",
      "log_id": 2,
      "room_code": "c0b2462c",
      "amount": 100000,
      "created_at": "2024-06-21T15:49:58Z",
      "modified_at": "2024-06-21T15:49:58Z"
    },
    {
      "username": "lizzy",
      "memo": "",
      "log_id": 1,
      "room_code": "c0b2462c",
      "amount": 100000,
      "created_at": "2024-06-21T15:42:27Z",
      "modified_at": "2024-06-21T15:42:27Z"
    }
  ],
  "balance": {
    "amount": 200000,
    "username": "lizzy"
  }
}
```

### 로그 변경

#### request

```http request
PUT http://localhost:8080/api/log
Content-Type: application/json

{
  "log_id": 16,
  "room_code": "c0b2462c",
  "username": "zeus",
  "amount": 2000,
  "memo": "foo bar"
}
```

#### response

```http request
HTTP/1.1 200
```

### 로그 삭제

#### request

```http request
DELETE http://localhost:8080/api/log
Content-Type: application/json

{
  "log_id": 3,
  "room_code": "c0b2462c",
  "username": "zeus"
}
```

#### response

```http request
HTTP/1.1 204
```
