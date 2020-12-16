# Web Crawler :spider: :spider_web:
<a href="https://github.com/badges/shields"><img src="https://img.shields.io/github/issues/chiritagabriela/WebCrawlerMTA" alt="Star on GitHub"></a>
<a href="https://github.com/badges/shields"><img src="https://img.shields.io/github/issues/chiritagabriela/WebCrawlerMTA?color=blue&label=MTAWebCrawl" alt="Star on GitHub"></a>

The software is designed to provide basic functionality of a Web Crawler. A web crawler is an Internet bot that helps at web indexing.These crawls one page at a time through a website, until all of the pages have been indexed.<br/>
After the resorces have been download is completed, the user will be informed about the success rate of the download and will be able to choose from one of the many data analysis methods like:

:trident: filtering data by type,dimension and date;<br/>
:trident: searching by keywords;<br/>
:trident: creating a sitemap;<br/>

## SRS & SDD

To see details about the <strong>Software Requirements Specification</strong> access the link:[SRS](https://github.com/chiritagabriela/WebCrawlerMTA/blob/master/SRS_C114D_Grupa1.pdf).<br/>
To see details about the <strong>Software Design Description</strong> access the link [SDD](https://github.com/chiritagabriela/WebCrawlerMTA/blob/master/SDD_C114D_Grupa1.pdf).<br/>

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
## Testing
In order to use the application, please make sure you first see the <strong>testing documentation</strong> [Testing](https://github.com/chiritagabriela/WebCrawlerMTA/blob/master/TestareAplicatie_C114D_Grupa1.docx)

## Team
Andrei Claudia :woman_student:

Chiriță Gabriela :woman_student:

Guțu Bogdan :man_student:

Manea Sebastian :man_student:

Mercheș Diana :woman_student:
