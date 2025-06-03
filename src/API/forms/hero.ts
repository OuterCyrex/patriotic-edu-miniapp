export interface HeroListReq {
  pageNum: number;
  pageSize: number;
  period?: number;
  name?: string;
}

export interface HeroList {
  list: HeroInfo[]
  total: number
  current: number
  size: number
  sizes: number
}

export interface HeroInfo {
  id: number
  name: string
  period: number
  periodYears: string
  title: string
  famousQuote: string
  summary: string
  sacrificeYear: string
  avatarUrl: string
}

export interface HeroDetail {
  id: number
  name: string
  period: number
  periodYears: string
  title: string
  famousQuote: string
  summary: string
  story: string
  sacrificeYear: string
  avatarUrl: string
}
