import {HeroDetail, HeroList, HeroListReq} from "@/API/forms/hero";

export interface ApiMap {
  '/hero/list': {
    req: HeroListReq;
    resp: HeroList;
  }
  '/hero/:id': {
    req: {id: number};
    resp: HeroDetail;
  }
}

export type ApiReq<K extends keyof ApiMap> = ApiMap[K]['req']
export type ApiResp<K extends keyof ApiMap> = {
  data: ApiMap[K]['resp']
  code: number
  message: string
}
