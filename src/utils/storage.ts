import Taro from "@tarojs/taro";
import {UserInfo} from "@/types/forms/user";

export const getUserInfoFromStorage = async () => {
  const userInfo = await Taro.getStorage({ key: 'user' })
    .then(res => res.data)
    .catch(() => '')as UserInfo
  return userInfo ? userInfo : null
}
