# Web Crawler :spider: :spider_web:

The software is designed to provide basic functionality of a Web Crawler. A web crawler is an Internet bot that helps index the Web.These crawls one page at a time through a website, until all of the pages have been indexed.<br/>
At the end of downloading the resources, the user will be informed about the success rate of the download and will have many methods of data analysis: 

:trident: filtering data by type,dimension and date;<br/>
:trident: searching by keywords;<br/>
:trident: creating a sitemap;<br/>

## SRS & SDD

To see details about the <strong>Software Requirements Specification</strong> access the link:[SRS]().<br/>
To see details about the <strong>Software Design Description</strong> access the link:[SDD]().<br/>

## How to use application? :computer: :keyboard:
Set crawling configuration file
```bash
crawl config.cnf
```

Crawling sites from a given input file
```bash
crawl input.txt
```
Crawling sites from a given input file with a specified type
```bash
crawl input.txt type png
```
Filter downloaded sites by type
```bash
filter type png
```
Filter downloaded sites by date
```bash
filter date zz/mm/yyyy
```
Filter downloaded sites by dimension
```bash
filter size 150 kb
```
Create sitemap in an output file
```bash
sitemap sitemap.txt
```
Search word in all the downloaded files
```bash
search word
```
Exit
```bash
exit
```

## Team
Andrei Claudia :woman_student:

Chiriță Gabriela :woman_student:

Guțu Bogdan :man_student:

Manea Sebastian :man_student:

Mercheș Diana :woman_student: