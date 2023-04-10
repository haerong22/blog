provider "aws" {
  region = "ap-northeast-3"
}

data "aws_availability_zones" "available" {
  state = "available"
}
