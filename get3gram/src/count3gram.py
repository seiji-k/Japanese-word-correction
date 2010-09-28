# -*- coding: utf-8 -*-

import codecs
import re

inputfile = '/home/seiji-k/data/wakati/xx03'

fin = codecs.open(inputfile, 'r','utf_8')
fout = codecs.open(inputfile+'out.txt', 'w', 'utf_8')
regex = (u' が 'u'| の 'u'| を 'u'| に 'u'| と 'u'| から 'u'| より 'u'| で ')
p = re.compile(regex)


for line in fin:
    b = p.search(line)
    if b:
        fout.write(line)
fin.close()
fout.close()
print 'done'