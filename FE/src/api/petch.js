import { localAxios } from "@/util/http-commons";
import { useUserStore } from "@/store/userStore";

const local = localAxios();

async function getPetch(success, fail) {
  local.defaults.headers["Authorization"] = "Bearer " + useUserStore().accessToken;
  await local.get(`/board/list`).then(success).catch(fail)
}

export { getPetch };