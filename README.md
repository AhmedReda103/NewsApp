# NewsApp
News app is an application that display news for categories like ( sports - healthy - bussiness ..) from a lot of sources uses newsApi.org 

# Preview

<div>
<img src="https://user-images.githubusercontent.com/95850640/235009584-9fb19af6-c981-4be5-b34a-c459be5f53f0.jpg" width="140" height="320" >
<img src="https://user-images.githubusercontent.com/95850640/235008915-c8a33207-80de-4cea-bedf-4b8f1cdd4a16.jpg" width="140" height="320" >



<img src="https://user-images.githubusercontent.com/95850640/235009712-e578d06e-994f-4967-9d43-f3c4c9b50c80.jpg" width="140" height="320" >

<img src="https://user-images.githubusercontent.com/95850640/235009776-ba583129-cd83-4c94-8b60-e2ac3437c9cd.jpg" width="140" height="320" >

<img src="https://user-images.githubusercontent.com/95850640/235009868-7ca6507d-ab4a-4051-8c13-7bde6700fcf6.jpg" width="140" height="320" >


</div>



# Libraries and technologies used

- Navigation component 

- Coroutines 

- Data binding 

- View binding

- ROOM Database

- Retrofit

- Dependency Injection - Dagger-Hilt

- Flow 

- LiveData

- Material Components

- ViewModel

- cache responses with room 

- solid principles


## API Reference

#### Get sources by category

```http
  GET https://newsapi.org/v2/top-headlines/sources
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `api_key` | `string` | **Required**. Your API key |
| `category`  | `string` | 	Find sources that display news of this category. Possible options: business entertainment general health science sports technology. Default: all categories|

#### get news by specific source 

```http
  GET https://newsapi.org/v2/everything
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `api_key` | `string` | **Required**. Your API key |
| `sources`  | `string` | identifiers for the news sources or blogs you want headlines from. Use the /sources endpoint to locate these programmatically|





## Environment Variables

To run this project, you will need to add `API_KEY`
