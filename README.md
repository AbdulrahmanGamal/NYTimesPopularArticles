# NYTimes Popular Articles
Simple App to display most popular articles from the newyork times 
I used  MVVM  architectural pattern and navigation components  i followed clean archtecture guideline to make code more testable it have
1. WebService Layer
2. Repository Layer ( will manuplate data from online api and local storage later )
3. View Layer contain viewmodel state based pattern and livedata as a state holder also used fragments and navigation components  


## Technologies used 
1. Kotlin  
2. Coroutine
3. Koin  for dependency injection
4. Retrofit
5. Mockk for unit testing
6. Kakao espresso based UI testing libarary  
7. Roboelectric for mocking android context 

## Future Enhancements 
1. save data locally to database and use Room library to see data offline
2. add pagination feature and use paging3 library with it's powerfull mediator
3. add more unit tests to cover more lines of code 

