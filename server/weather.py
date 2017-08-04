import requests
from bs4 import BeautifulSoup
from datetime import datetime
import calendar


def weather(year: int, month: int, stn=108):
    # stn=stn(지역), &yy=year(년), &mm=month(월) <= 모두 int

    urls = ["http://www.kma.go.kr/weather/climate/past_cal.jsp?stn={0}&yy={1}&mm={2}&obs=1".format(stn, year, month),
            "http://www.kma.go.kr/weather/climate/past_cal.jsp?stn={0}&yy={1}&mm={2}&obs=9".format(stn, year, month)]
    # urls[0](기온/강수량), urls[1](날씨)

    response = requests.get(urls[1])
    dom = BeautifulSoup(response.text, "html.parser")
    elements = dom.select("tbody tr")

    yy = datetime.today().year
    mm = datetime.today().month
    dd = datetime.today().day
    end_day = calendar.monthrange(year, month)[1]

    if year > yy or (year == yy and month > mm):
        return

    skip = True
    cnt = 0
    day = 0

    for element in elements:
        tds = element.select("td")

        for td in tds:
            if month >= mm and day >= dd - 1 or day >= end_day:
                break
            elif skip:
                if td.text == " ":
                    cnt = cnt + 1
            elif td.text == " " and cnt > 0:
                cnt = cnt - 1
            elif td.text != " ":
                day = day + 1
                for br in td.find_all("br"):
                    br.replace_with("\n")
                print(str(day) + '. ' + td.text + "\n")
            else:
                day = day + 1
                print(str(day) + '. 맑음\n')

        if skip:
            skip = False
        else:
            skip = True


weather(2017, 8)
