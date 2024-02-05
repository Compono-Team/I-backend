CONTAINER_NAME=i-backend-dev

# 도커 컨테이너가 있는지 확인 (-a 옵션으로 정지된 컨테이너도 확인)
RUNNING_CONTAINER_ID=$(sudo docker ps -aq --filter "name=$CONTAINER_NAME")
echo "실행중인 컨테이너 ID: $RUNNING_CONTAINER_ID"

# 이전 도커 컨테이너 종료
if [ -n "$RUNNING_CONTAINER_ID" ]; then
  echo "이전 도커 컨테이너 종료 및 삭제합니다"
  sudo docker stop $CONTAINER_NAME && sudo docker rm $CONTAINER_NAME
fi
