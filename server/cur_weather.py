import pyowm


# 자신의 API_Key로 OWM에 접근할수있도록 Global OWM Object를 만듭니다.
api_key = '5922ee4c174a12611c8de512f8829031'
owm = pyowm.OWM(API_key=api_key, language="ja")
f = open('city_list.txt', 'r')

# to delete head line
f.readline()

ID = {}
lines = f.readlines()

for line in lines:
    items = line.split('	')
    ID[items[1]] = int(items[0])

f.close()

# TODO: Select city by user
# TODO: 한글화
City_name = 'Incheon'

obs = owm.weather_at_id(ID[City_name])

# get_weather는 기상정보에 대한 정보를 가져옵니다.
W = obs.get_weather()
Temp = W.get_temperature(unit='celsius')
print(City_name + '의 최고기온은 ' + str(Temp['temp_max']) + ' 도 입니다.')
print(City_name + '의 최저기온은 ' + str(Temp['temp_min']) + ' 도 입니다.')
print(City_name + '의 현재기온은 ' + str(Temp['temp']) + ' 도 입니다.')

Status = W.get_status()
print(City_name + '의 현재날씨는 ' + Status + ' 입니다.')
