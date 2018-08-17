package com.example.olskr.weatherhm2;

// Это Presenter, делаем его на основе паттерна "одиночка"
// Этот паттерн обладает свойством, хранить один экземпляр объекта на все приложение.
// Для реализации паттерна одиночка, надо добавить статическое приватное поле (instance)
// конструктор сделать приватным
// добавить статический метод, который проверяет, существует ли этот объект в нашем поле, если нет,
// то создает его. И возвращает это поле.
// Таким образом в пролижении всегда существует только один объект.
// Класс, реализующий синглтон нельзя наследовать.
public final class WeatherPresenter {
    // Внутреннее поле, будет хранить единственный экземпляр
    private static WeatherPresenter instance = null;

    // Это наше название грода
    private String nameCity;

    // Конструктор (вызывать извне его нельзя, поэтому он приватный)
    private WeatherPresenter(){
    }

    // инициализация
    public void initializationNameCity(String nameCity){
       this.nameCity = nameCity;
    }

    public String getNameCity(){
        return nameCity;
    }

    // Метод, который возвращает экземпляр объекта
    // Если объекта нет, то его создаем.
    public static WeatherPresenter getInstance(){
        // Здесь реализована "ленивая" инициализация объекта
        // то есть, пока объект не нужен, создавать его не будем.
        if (instance == null) instance = new WeatherPresenter();
        return instance;
    }
}
