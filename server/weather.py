import requests
from bs4 import BeautifulSoup

def getWeather(year, mm, stn='108'):
    urls = ["http://www.kma.go.kr/weather/climate/past_cal.jsp?stn={0}&yy={1}&mm={2}&obs=1".format(stn, year, mm),
            "http://www.kma.go.kr/weather/climate/past_cal.jsp?stn={0}&yy={1}&mm={2}&obs=9".format(stn, year, mm)]
    # urls[0](기온/강수량), urls[1](날씨)

    # stn=stn(지역), &yy=year(년), &mm=mm(월)
    response = requests.get(urls[1])

    dom = BeautifulSoup(response.text, "html.parser")
    elements = dom.select("tbody tr")

    skip = True
    cnt = 0
    # date = 0
    for element in elements:
        # TODO: 오늘 날짜 가져와서 이전 날짜만 출력하기
        # TODO: date 변수로 날짜도 출력하기
        tds = element.select("td")

        for td in tds:
            if skip:
                if td.text == " ":
                    cnt = cnt+1
                continue
            if td.text == " " and cnt > 0:
                cnt = cnt-1
            elif td.text != " ":
                for br in td.find_all("br"):
                    br.replace_with("\n")
                print(td.text + "\n")
            else:
                print('맑음\n')

        if skip:
            skip = False
        else:
            skip = True

getWeather('2017', '7')
