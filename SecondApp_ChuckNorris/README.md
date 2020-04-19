# Second application

At the end of this [lesson](https://github.com/NicolasDuponchel/TeachingAndroid/tree/master/ChuckNorrisJokes), my application can display a list of jokes fetched from a [web API](https://api.chucknorris.io/). We can then share, save or remove them from the application. It also manages screen rotation.

To be able to do that, I used :
- **RecyclerView** for the dynamic list
- **Retrofit** to fetch jokes from the web API
- **Kotlinx Serialization** to deal with Json data structure
- **RxJava** for the asynchronous code