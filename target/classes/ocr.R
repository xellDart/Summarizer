#!/usr/bin/env Rscript
library(magick)
library(magrittr)

spanish <- tesseract("spa")
text <- image_read(paste(getwd(), "/src/main/resources/text.jpg", sep = ""), engine = spanish) %>%
    image_resize("2000") %>%
    image_convert(colorspace = 'gray') %>%
    image_trim() %>%
    image_ocr()

cat(text)
