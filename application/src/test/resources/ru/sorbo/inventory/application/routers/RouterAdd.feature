#language: ru

@RouterAdd
Функция: Возможность связывать роутеры разного типа между собой
  Сценарий: Добавление внутреннего роутера к основному роутеру
    Дано У меня есть внутренний роутер
    И У меня есть основной роутер
    Тогда Я добавляю внутренний роутер к основному роутеру

  Сценарий: Добавление основного роутера к другому основному роутеру
    Дано У меня есть этот основной роутер
    И У меня есть другой основной роутер
    Тогда Я добавляю этот основной роутер к другому основному роутеру