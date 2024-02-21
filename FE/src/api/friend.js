import { localAxios } from "@/util/http-commons";
import { useUserStore } from "@/store/userStore";

const local = localAxios();

async function searchUser(searchName, success, fail) {
  local.defaults.headers["Authorization"] = "Bearer " + useUserStore().accessToken;
  await local.get(`/user/search/${searchName}`).then(success).catch(fail)
}

async function getMyFriend(success, fail) {
  local.defaults.headers["Authorization"] = "Bearer " + useUserStore().accessToken;
  await local.get(`/friend/myfriend`).then(success).catch(fail);
}

async function getMyFriendRequest(success, fail) {
  local.defaults.headers["Authorization"] = "Bearer " + useUserStore().accessToken;
  await local.get(`/friend/receive`).then(success).catch(fail)
}

async function requestFriend(friendInfo, success, fail) {
  local.defaults.headers["Authorization"] = "Bearer " + useUserStore().accessToken;
  await local.post(`/friend/send`, JSON.stringify(friendInfo)).then(success).catch(fail)
}

async function acceptFriend(friendInfo, success, fail) {
  local.defaults.headers["Authorization"] = "Bearer " + useUserStore().accessToken;
  await local.post(`/friend/accept`, JSON.stringify(friendInfo)).then(success).catch(fail)
}

async function rejectFriend(friendInfo, success, fail) {
  local.defaults.headers["Authorization"] = "Bearer " + useUserStore().accessToken;
  await local.post(`/friend/reject`, JSON.stringify(friendInfo)).then(success).catch(fail)
}

export { searchUser, getMyFriend, getMyFriendRequest, requestFriend, acceptFriend, rejectFriend };
