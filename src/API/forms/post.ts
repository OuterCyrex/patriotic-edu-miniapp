export interface NewPostReq {
  content: string
  region: string
  identity: string
  authorName: string
  theme: string
}

export interface PostListReq {
  pageNum: number;
  pageSize: number;
  key?: string;
}

export interface PostList {
  list: Array<PostInfo>
  total: number
  current: number
  size: number
  pages: number
}

export interface PostInfo {
  id: number
  content: string
  region: string
  identity: string
  authorName: string
  theme: string
  likesCount: number
  commentsCount: number
  isFeatured: number
}

export interface CommentListReq {
  pageNum: number;
  pageSize: number;
  voiceId: number;
}

export interface CommentList {
  list: CommentInfo[]
  total: number
  current: number
  size: number
  sizes: number
}

export interface CommentInfo {
  id: number
  voiceId: number
  userId: number
  content: string
  parentId: number
  likesCount: number
}

export interface NewCommentReq {
  id: number
  content: string
  type: number
  replyId: number
}

export interface WordFrequency {
  id: number
  content: string
  frequency: number
  status: number
}
