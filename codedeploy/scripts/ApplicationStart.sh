IMAGE_NAME=_IMAGE_NAME_
AWS_REGION=_AWS_REGION_
AWS_ECR_URI=_AWS_ECR_URI_
DEPLOY_DIR=_DEPLOY_DIR_
SERVICE_NAME=i-backend-dev-service

sudo aws ecr get-login-password --region ${AWS_REGION} | sudo docker login --username AWS --password-stdin ${AWS_ECR_URI}
sudo docker image prune -f
sudo docker pull ${IMAGE_NAME}

# 새로운 도커 컨테이너 실행
echo "IMAGE_NAME: $IMAGE_NAME 도커 실행"
cd ${DEPLOY_DIR}
sudo docker compose up -d $SERVICE_NAME
