export interface UserRegisterReq {
  username: string
  nickname: string
  avatarUrl: string
  region: string
  password: string
  code: string
}

export interface UserLoginReq {
  username: string
  password: string
}

export interface UserInfo {
  id: number
  username: string
  nickname: string
  avatarUrl: string
  region: string
  type: number
  totalStars: number
  token: string
}
