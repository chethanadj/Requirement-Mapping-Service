import nltk
from nltk.collocations import BigramCollocationFinder
from nltk.tokenize import word_tokenize , sent_tokenize
from nltk.corpus import stopwords
from string import punctuation
from nltk.stem.lancaster import LancasterStemmer
from nltk.corpus import wordnet as wn
from nltk.wsd import lesk
import json

text="In computer security, logging in, (or logging on or signing in or signing on), is the process by which an individual gains access to a computer system by identifying and authenticating themselves. The user credentials are typically some form of \"username\" and a matching \"password\",[1] and these credentials themselves are sometimes referred to as a login, (or a logon or a sign in or a sign on).[2][1] In practice, modern secure systems also often require a second factor for extra security."
custom_stop_words=set(stopwords.words('english')+list(punctuation))

def get_sent_tokenize(arg):
    return sent_tokenize(arg)


def get_word_tokenize(arg):
    return word_tokenize(arg)


def get_word_tokenize_without_stopwords(arg):
    return [word for word in get_word_tokenize(arg) if word not in custom_stop_words]


def get_bigrams(arg):
    bigram_measures=nltk.collocations.BigramAssocMeasures()
    finder=BigramCollocationFinder.from_words(get_word_tokenize_without_stopwords(arg))
    return sorted(finder.ngram_fd.items())


def get_lancaster_stemmed_words(arg):
    st=LancasterStemmer()
    return [st.stem(word) for word in word_tokenize(arg)]


def get_pos_tagged_words(arg):
    return nltk.pos_tag(word_tokenize(arg))


def get_wn_definition(arg):
    return wn.synsets(arg)


def get_wn_meaning(arg1,arg2):
    sensel=lesk(word_tokenize(arg1),arg2)
    return (sensel.definition())


def get_custom_filtered_words(arg):
    words = [word[0] for word in get_pos_tagged_words(arg) if "NN" in word[1] or "VB" in word[1]]
    st = LancasterStemmer()
    list=[st.stem(word) for word in words if word not in custom_stop_words]
    print (list)
    return json.dumps(list)


# print(get_custom_filtered_words(text))
