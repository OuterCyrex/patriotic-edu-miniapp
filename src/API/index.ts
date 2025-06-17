import {request} from "@/API/request";
import {HeroListReq} from "@/types/forms/hero";
import {CommentListReq, NewCommentReq, NewPostReq, PostListReq} from "@/types/forms/post";
import {UserLoginReq, UserRegisterReq} from "@/types/forms/user";
import {getUserInfoFromStorage} from "@/utils/storage";

export const hero = {
  HeroList: (req: HeroListReq) => request('/hero/list', {
    method: 'GET',
    data: req,
  }),
  HeroDetail: (req: {id: number}) => request('/hero/:id',  {
    method: 'GET',
    pathParams: {id: req.id}
  }),
}

export const post = {
  NewPost: (req: NewPostReq) => request('/voice/submit', {
    method: 'POST',
    data: req,
    withToken: true
  }),
  PostList: (req: PostListReq) => request('/voice/list', {
    method: 'GET',
    data: req
  }),
  GetPostDetail: (req: {id: number}) => request('/voice/:id', {
    method: 'GET',
    pathParams: {id: req.id}
  }),
  PostLike:  (req: {targetType: number, targetId: number}) => request('/voice/like', {
    method: 'POST',
    data: req,
    withToken: true
  }),
  CommentList: (req: CommentListReq) => request('/voice/comment', {
    method: 'GET',
    data: req
  }),
  NewComment: (req: NewCommentReq) => request('/voice/comment', {
    method: 'POST',
    data: req,
    withToken: true
  }),
  GetWordFrequency: (req: {x: number}) => request('/word/top/:x', {
    method: 'GET',
    pathParams: {x: req.x}
  }),
  GetLikeList: () => request('/voice/likes', {
    method: "GET",
    data: {voiceId: -1},
    withToken: true
  })
}

export const user = {
  GetUserInfo: () => getUserInfoFromStorage(),
  SendEmail: (req: {identifier: string}) => request('/user/sendCode', {
    method: 'GET',
    data: req
  }),
  Register: (req: UserRegisterReq) => request('/user/register', {
    method: 'POST',
    data: req
  }),
  Login: (req: UserLoginReq) => request('/user/login', {
    method: 'POST',
    data: req
  }),
  UpdateUser: (req: {id: number, nickname: string, avatarUrl: string, region: string}) => request('/user/update', {
    method: "PUT",
    data: req
  })
}

export const question = {
  GetKnowledge: () => request('/question/knowledge', {
    method: "GET",
    withToken: true
  }),
  SubmitKnowledge: (req: Array<{questionId: number, answer: number}>) => request('/question/knowledgeAns', {
    method: 'POST',
    data: req,
    withToken: true
  }),
  GetScenario: () => request('/question/scenario', {
    method: 'GET',
    withToken: true,
  }),
  SubmitScenario: (req: {questionId: number, answer: number}) => request('/question/scenarioAns', {
    method: 'GET',
    data: req,
    withToken: true,
  })
}

export const system = {
  GetSystemConfig: () => request('/system', {
    method: 'GET',
  }),
  GetAnnouncementList: (req: {pageNum: number, pageSize: number}) => request('/system/list', {
    method: 'GET',
    data: req
  }),
  GetAnnouncementDetail: (req: {id: number}) => request('/system/:id', {
    method: 'GET',
    pathParams: {id: req.id}
  }),
  GetMisc: (req: {key: string}) => request('/system/misc', {
    method: 'GET',
    data: req
  })
}
