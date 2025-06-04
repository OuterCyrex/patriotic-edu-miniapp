import {HeroDetail, HeroList, HeroListReq} from "@/API/forms/hero";
import {CommentList, CommentListReq, NewPostReq, PostInfo, PostList, PostListReq} from "@/API/forms/post";

export interface ApiMap {
  '/hero/list': {
    req: HeroListReq
    resp: HeroList
  }
  '/hero/:id': {
    req: {id: number}
    resp: HeroDetail
  }
  '/voice/submit': {
    req: NewPostReq
    resp: {}
  }
  '/voice/list': {
    req: PostListReq
    resp: PostList
  }
  '/voice/:id': {
    req: {id: number}
    resp: PostInfo
  }
  '/voice/like/:id': {
    req: {id: number}
    resp: {}
  }
  '/voice/comment': {
    req: CommentListReq
    resp: CommentList
  }
}

export type ApiReq<K extends keyof ApiMap> = ApiMap[K]['req']
export type ApiResp<K extends keyof ApiMap> = {
  data: ApiMap[K]['resp']
  code: number
  message: string
}
