import { localAxios } from "@/util/http-commons";
import { useUserStore } from "@/store/userStore";

const local = localAxios();

function getRoomList(success, fail){
  local.get('/room').then(success).catch(fail);
}

function getRoomDetail(roomId, success, fail){
  local.get('/room/detail/' + `${roomId}`).then(success).catch(fail);
}

function getCanEnterRoom(roomId, password, success, fail){
  local.defaults.headers["Authorization"] = "Bearer " + useUserStore().accessToken;
  local.post('/room/' + `${roomId}`, password).then(success).catch(fail);
}

function axiosCreateRoom(createRoomInfo, success, fail){
  local.defaults.headers["Authorization"] = "Bearer " + useUserStore().accessToken;
  local.post('/room/create', JSON.stringify(createRoomInfo),{
      headers: {
          "Content-Type": "application/json; charset=utf-8",
      },
  }).then(success).catch(fail)
}

export {getRoomList, getRoomDetail, getCanEnterRoom, axiosCreateRoom};