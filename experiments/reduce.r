#! /usr/bin/env Rscript

temp <- read.csv("stdin", sep=",", header=TRUE)
#cat(min(d), max(d), median(d), mean(d), sep="\n")
data <- as.matrix(temp)
write.csv(t(colMeans(data)), row.names = FALSE)

