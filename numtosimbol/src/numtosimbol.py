# -*- coding: utf-8 -*-

import codecs
import re
import sys
sys.stdout = codecs.getwriter('utf_8')(sys.stdout)


inputfile = '/home/seiji-k/data/wakati/jawiki_w.txt_cleaned'

fin = codecs.open(inputfile, 'r','utf_8')
fout = codecs.open(inputfile + '_numed', 'w', 'utf_8')
p = re.compile(u'\d\d*')

for line in fin:
    #ngram = re.split(u'\s', line)
    #for word in ngram:
        #if re.match(p, word):
    b = p.sub("<num>", line)
        #print word.encode('utf-8')
    print b.encode('utf-8')
    fout.write(b.encode('utf-8'))
fin.close()
fout.close()
print 'done'