import {HeroDetail, HeroList, HeroListReq} from "@/types/forms/hero";
import {
  CommentList,
  CommentListReq,
  NewCommentReq,
  NewPostReq,
  PostInfo,
  PostList,
  PostListReq, WordFrequency
} from "@/types/forms/post";
import {UserLoginReq, UserRegisterReq, UserInfo} from "@/types/forms/user";
import {KnowledgeItem, ScenarioItem, SubmitKnowledgeList} from "@/types/forms/question";

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
  '/question/knowledge': {
    req: {}
    resp: Array<KnowledgeItem>
  }
  '/question/knowledgeAns': {
    req: Array<{questionId: number, answer: number}>
    resp: SubmitKnowledgeList
  }
  '/question/scenario': {
    req: {},
    resp: Array<ScenarioItem>
  }
  '/question/scenarioAns': {
    req: {questionId: number, answer: number}
    resp: { legalBasis: string, solution: string, result: boolean, recordId: number, answer: number, userAnswer: number }
  }
  '/voice/like': {
    req: { targetType: number, targetId: number}
    resp: {}
  }
  '/voice/likes': {
    req: {voiceId: number}
    resp: Array<number>
  }
}

export type ApiReq<K extends keyof ApiMap> = ApiMap[K]['req']
export type ApiResp<K extends keyof ApiMap> = {
  data: ApiMap[K]['resp']
  code: number
  message: string
}
