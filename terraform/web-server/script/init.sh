#/bin/bash

sudo yum update -y
sudo yum install docker -y
sudo systemctl start docker
sudo usermod -aG docker ec2-user
sudo systemctl enable docker
sudo docker run --name=nginx -d -p 80:80 nginx