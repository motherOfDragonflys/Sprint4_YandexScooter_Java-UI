АВТОТЕСТЫ для учебного сервиса Яндекс Самокат  

Структура классов:   

src/main - локаторы, внутренняя логика, утилиты и входные данные:   

   - src/main/java/locators  
      - BaseLocators содержит основные локаторы для всех страниц реализации  
      - Остальные: содержат локаторы для конкретных страниц и форм  
   - src/main/java/pageobjects  
     - BasePage содержит логику, общую для всех страниц  
      - OrderAllFormsPage содержит логику, общую для всех форм  
      - Остальные: содержат логику для конкретных страниц и форм  
   - src/main/java/utils  
      - JsonReader - Общая утилита чтения JSON файлов  
      - TestDataLoader - для загрузки тестовых данных с валидацией ключей  
      - UrlUtils - Утилита для проверки Url  
   - src/main/resources (json файлы)  
      - faq-questions эталоны для вопросов и ответов  
      - positive-order-scooter-options-form валидные данные для формы опций заказа  
      - positive-order-scooter-persona-form валидные данные для формы пользовательских данных  
      
src/test/java - автотесты  

   - src/test/java/basetest/BaseTest стандартные настройки браузера, константы URL, Before|After  
   - src/test/java/test  
      - ErrorsForFieldsOrderFormsTests - появление ошибки для полей формы заказа (реализация - первая форма)  
      - FaqTest - соответствие ответов вопросам, наличие всех вопросов из эталона json  
      - LogoTests - лого Самокат ведет на главную страницу Самоката, Яндекс - Яндекса  
      - OrderPositiveFlowTest - полный позитивный флоу для заказа самоката  
      - SearchIncorrectOrderNumberTest - сценарий поиска заказа по несуществующему номеру  
