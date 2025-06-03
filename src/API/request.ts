// utils/request.ts
import Taro from '@tarojs/taro'
import type { ApiMap, ApiReq, ApiResp } from './types'

type HttpMethod = 'GET' | 'POST' | 'PUT' | 'DELETE' | 'PATCH'  // 常见 HTTP 方法

const BaseURL = 'http://106.52.199.168:8080'

export async function request<K extends keyof ApiMap>(
  url: K | string,
  options: {
    method: HttpMethod
    data?: ApiReq<K>
    config?: Partial<Taro.request.Option>
    pathParams?: Record<string, string | number>
  }
): Promise<ApiResp<K>> {
  let fullUrl = `${BaseURL}${url as string}`

  if (options.pathParams) {
    Object.entries(options.pathParams).forEach(([key, value]) => {
      fullUrl = fullUrl.replace(new RegExp(`:${key}\\b`, 'g'), encodeURIComponent(String(value)))
    })
  }

  const res = await Taro.request<ApiResp<K>>({
    url: fullUrl,
    method: options.method as Taro.request.Option['method'],
    ...(options.method === 'GET' ? { data: options.data as any } : { data: options.data }),
    ...options.config,
    header: {
      'Content-Type': 'application/json',
      ...(options.config?.header ?? {}),
    },
  })

  if (res.statusCode >= 200 && res.statusCode < 300) {
    return res.data
  } else {
    throw new Error(`HTTP ${res.statusCode}: ${(res.data as any)?.message || 'Request failed'}`)
  }
}

