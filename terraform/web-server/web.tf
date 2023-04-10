resource "aws_instance" "web" {
	ami		                    = "ami-012c6a03c2e59a445"
	instance_type	            = "t3.small"
	subnet_id	                = aws_subnet.subnet.id
	vpc_security_group_ids      = [aws_security_group.sg.id]
	key_name	                = aws_key_pair.kp.key_name	
	associate_public_ip_address = true
 
	provisioner "file" {
	source = "./script/init.sh"
		destination = "/home/ec2-user/init.sh"

		connection {
			type        = "ssh"
			host        = self.public_ip
			user        = "ec2-user"
			private_key = tls_private_key.pk.private_key_pem
		}
 	}

  	provisioner "remote-exec" {
    		inline = [
      			"chmod +x /home/ec2-user/init.sh",
      			"/home/ec2-user/init.sh"
    		]

    		connection {
      			type        = "ssh"
      			host        = self.public_ip
      			user        = "ec2-user"
      			private_key = tls_private_key.pk.private_key_pem
    		}
  	}

	tags = {
		Name = "web-server" 
	}
}

resource "aws_eip" "web" {
  vpc        = true
  depends_on = [aws_internet_gateway.igw]
}

resource "aws_eip_association" "web" {
  instance_id   = aws_instance.web.id
  allocation_id = aws_eip.web.id
}

output "web_public_ip" {
  value = aws_eip.web.public_ip
}
