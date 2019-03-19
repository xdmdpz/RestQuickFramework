#! /bin/bash
sudo docker stop nebuladata_zimg
sudo docker rm nebuladata_zimg
sudo docker run -it -d -p 4869:4869 -v /upload/img:/zimg/bin/img --name zimg iknow0612/zimg