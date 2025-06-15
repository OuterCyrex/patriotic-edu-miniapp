import type {ApiMap, ApiResp} from "@/API/types";
import {showToast} from "@tarojs/taro";

export async function useApi<K extends keyof ApiMap> (
  option: {
    api: Promise<ApiResp<K>>,
    onSuccess ? : (output: ApiResp<K>) => void,
    onFail ? : () => void
},
): Promise<void> {
  const resp = await option.api
  if (resp.code !== 200) {
    if (option.onFail !== undefined) {
      option.onFail()
    } else {
      await showToast({ title: "服务器错误", icon: "error" })
      console.error(resp.message)
    }
  } else {
    option.onSuccess?.(resp)
  }
}

