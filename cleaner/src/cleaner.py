# -*- coding: utf-8 -*-

import codecs
import re
import sys
sys.stdout = codecs.getwriter('utf_8')(sys.stdout)


inputfile = '/home/seiji-k/data/wakati/jawiki_w.txt'

fin = codecs.open(inputfile, 'r','utf_8')
fout = codecs.open(inputfile + '_cleaned', 'w', 'utf_8')
regex =  u"[\u2010-\u301F]|[\uFF01-\uFF0F]|[\uFF1A-\uFF1F]|[\uFF3B-\uFF40]|[\uFF5B-\uFFE5]|[\u0021-\u002F]|[\u003A-\u0040]|[\u005B-\u0060]|[\u007B-\u203E]"
p = re.compile(regex)


for line in fin:
    #ngram = line.split('\t')
    b = p.sub("", line)
    print b.encode('utf-8')
    fout.write(b.encode('utf-8'))
fin.close()
fout.close()
print 'done'