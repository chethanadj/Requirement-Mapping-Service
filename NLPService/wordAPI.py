from flask import Flask
from flask import request
from processor.sentenceProcessor import get_custom_filtered_words

app = Flask(__name__)


@app.route('/')
def hello_world():
    return 'Hello World!'


@app.route('/getwordlist' , methods=['POST', 'PUT '])
def get_filtered_word_list():
    description=request.json['description']
    res=get_custom_filtered_words(description)
    return res


# @app.route('/summarize' , methods=['POST' , 'PUT '])
# def get_summary():
#     url=request.json['url']
#     # res= summarizer.get_summary_text(url)
#     return res

if __name__ == '__main__':
    app.run()
