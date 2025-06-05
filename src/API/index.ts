import {request} from "@/API/request";
import {HeroListReq} from "@/API/forms/hero";
import {CommentListReq, NewCommentReq, NewPostReq, PostListReq} from "@/API/forms/post";

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
    data: req
  }),
  PostList: (req: PostListReq) => request('/voice/list', {
    method: 'GET',
    data: req
  }),
  PostDetail: (req: {id: number}) => request('/voice/:id', {
    method: 'GET',
    pathParams: {id: req.id}
  }),
  /**
   * @deprecated 此方法尚未完成
   */
  PostLike:  (req: {id: number}) => request('/voice/like/:id', {
    method: 'GET',
    pathParams: {id: req.id}
  }),
  CommentList: (req: CommentListReq) => request('/voice/comment', {
    method: 'GET',
    data: req
  }),
  NewComment: (req: NewCommentReq) => request('/voice/comment', {
    method: 'POST',
    data: req
  }),
  GetWordFrequency: (req: {x: number}) => request('/word/top/:x', {
    method: 'GET',
    pathParams: {x: req.x}
  })
}
