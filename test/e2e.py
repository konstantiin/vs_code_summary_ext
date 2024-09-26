from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.firefox.service import Service
from selenium.webdriver.firefox.options import Options
from selenium.webdriver import FirefoxProfile

import time
import os

import requests

def make_request(selected_option, input_text):
    url = 'http://127.0.0.1:8080/manage-summarization'
    headers = {
        'Content-Type': 'application/json; charset=UTF-8'
    }
    
    # Формируем данные для отправки
    data = {
        'modelName': selected_option,
        'textToSummarize': input_text
    }
    
    try:
        # Отправляем POST-запрос
        response = requests.post(url, headers=headers, json=data)
        
        # Проверяем код возврата и наличие данных в ответе
        if response.status_code == 200 and response.text.strip():  # Проверяем, что ответ не пустой
            return True
        else:
            return False
    except Exception as e:
        print(f"Ошибка при выполнении запроса: {e}")
        return False

def process_input(driver, url, input_text):
    """
    Обрабатывает входной текст, вводит его на веб-странице и проверяет наличие ошибки в outputText.

    :param driver: Веб-драйвер для взаимодействия с браузером.
    :param url: Страница которую нужно протестировать.
    :param input_text: Входящий текст для ввода в текстовое поле.
    :return: True, если вывод не содержит ошибку; False в противном случае.
    """
    try:
        # Открываем веб-страницу
        driver.get(url)

        # Находим текстовое поле по id и вводим текст
        input_box = driver.find_element(By.ID, 'inputText')
        input_box.send_keys(input_text)  # Вводим переданный текст

        # Находим и нажимаем кнопку submit
        submit_button = driver.find_element(By.ID, 'submitButton')
        submit_button.click()

        # Ждем появления текста в outputText
        output_box = WebDriverWait(driver, 10).until(
            EC.presence_of_element_located((By.ID, 'outputText'))
        )
        time.sleep(5)
        # Получаем текст из outputText
        output_text = driver.execute_script("return document.getElementById('outputText').value;")
        # Проверяем, что вывод не содержит ошибку
        #print(output_text)

        if "Ошибка при получении данных." not in output_text:
            print("Text successfully preocessed!")
            return True
        else:
            print("Input error found")
            return False

    except Exception as e:
        print(f"Error: {e}")
        return False

    finally:
        # Закрываем драйвер через некоторое время
        time.sleep(5)  # Ждем 5 секунд для просмотра результата
        #driver.quit()

import sys
from omegaconf import OmegaConf

def main(config_file):
    # Загружаем конфигурацию из YAML файла

    
    # Инициализация драйвера (например, для Chrome)
    options = Options()
    options.binary_location = "C:/Program Files/Mozilla Firefox/firefox.exe"

    # Initialize the Firefox driver
    #service = Service("D:/Code/utils/gecko/geckodriver.exe")  # Update with your path to geckodriver

    driver = webdriver.Chrome()
    #driver = webdriver.Firefox(service=service, options=options)

    config = OmegaConf.load(config_file)
    
    api_ans = 0
    for post in config.posts:
        if make_request(post.model_name, post.input_text):
            print("API request executed")
            api_ans += 1
        else:
            print("API request not executed")
    if api_ans == 0:
        print("API not available!")
    time.sleep(5)

    total_cases = len(config.cases)
    errors = 0
    
    # Обрабатываем каждый кейс
    i=0
    for case in config.cases:
        i+=1
        url = case.url
        input_text = case.input_text
        expected = case.expected
        
        result = process_input(driver, url, input_text)
        
        if result != expected:
            print(f"Error in test: {i}")
            errors += 1
        else:
            print(f"Passed test {i}")
    driver.quit()
    print(f"\nErrors: {errors} from {total_cases}")

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python script.py <path_to_config>")
        sys.exit(1)
    
    config_file_path = sys.argv[1]
    main(config_file_path)

