// utils/request.ts
import Taro, {showToast} from '@tarojs/taro'
import type { ApiMap, ApiReq, ApiResp } from './types'

type HttpMethod = 'GET' | 'POST' | 'PUT' | 'DELETE' | 'PATCH'

export const BaseURL = 'https://eduapp.mynatapp.cc'

export async function request<K extends keyof ApiMap>(
  url: K | string,
  options: {
    method: HttpMethod
    data?: ApiReq<K>
    config?: Partial<Taro.request.Option>
    pathParams?: Record<string, string | number>
    withToken?: boolean
  }
): Promise<ApiResp<K>> {
  let fullUrl = `${BaseURL}${url as string}`

  if (options.pathParams) {
    Object.entries(options.pathParams).forEach(([key, value]) => {
      fullUrl = fullUrl.replace(new RegExp(`:${key}\\b`, 'g'), encodeURIComponent(String(value)))
    })
  }

  const headers: Record<string, string> = {
    'Content-Type': 'application/json',
    ...(options.config?.header ?? {})
  }

  if (options.withToken) {
    const token = await Taro.getStorage({ key: 'user' })
      .then(res => res.data.token)
      .catch(() => {
        showToast({title: "请先登录", icon: "error"})
        setTimeout(() => Taro.navigateBack(), 1000)
      })
    if (token) {
      headers['Authorization'] = token
    }
  }

  const res = await Taro.request<ApiResp<K>>({
    url: fullUrl,
    method: options.method as Taro.request.Option['method'],
    ...(options.method === 'GET' ? { data: options.data as any } : { data: options.data }),
    ...options.config,
    header: headers
  })

  if (res.statusCode >= 200 && res.statusCode < 300) {
    return res.data
  } else {
    throw new Error(`HTTP ${res.statusCode}: ${(res.data as any)?.message || 'Request failed'}`)
  }
}


