import {HeroDetail, HeroList, HeroListReq} from "@/API/forms/hero";
import {
  CommentList,
  CommentListReq,
  NewCommentReq,
  NewPostReq,
  PostInfo,
  PostList,
  PostListReq, WordFrequency
} from "@/API/forms/post";
import {UserLoginReq, UserRegisterReq, UserInfo} from "@/API/forms/user";

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
    req: CommentListReq | NewCommentReq
    resp: CommentList | {}
  }
  '/word/top/:x': {
    req: {x: number}
    resp: Array<WordFrequency>
  }
  '/user/sendCode': {
    req: {identifier: string}
    resp: {}
  }
  '/user/register': {
    req: UserRegisterReq
    resp: {}
  }
  '/user/login': {
    req: UserLoginReq,
    resp: UserInfo
  }
  '/user/update': {
    req: {id: number, nickname: string, avatarUrl: string, region: string}
    resp: {}
  }
}

export type ApiReq<K extends keyof ApiMap> = ApiMap[K]['req']
export type ApiResp<K extends keyof ApiMap> = {
  data: ApiMap[K]['resp']
  code: number
  message: string
}
