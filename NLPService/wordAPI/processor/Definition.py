import urllib2
from bs4 import BeautifulSoup

activeURL = "https://techterms.com/definition/login"

page = urllib2.urlopen(activeURL).read().decode('utf8', 'ignore')
soup = BeautifulSoup(page, "lxml")

text = ' '.join(map(lambda p: p.text, soup.find_all('article')))


def getTextWapo(url):
    page = urllib2.urlopen(url).read().decode('utf8', 'ignore')
    soup = BeautifulSoup(page, "lxml")
    text = soup.article.p.text
    return text.encode('ascii', errors='replace').replace("?", " ")


print(getTextWapo(activeURL))
