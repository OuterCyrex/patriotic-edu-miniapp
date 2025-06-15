export interface AnnouncementList {
  list: Array<AnnouncementItem>
  total: number
  current: number
  size: number
  sizes: number
}

export interface AnnouncementItem {
  id: number
  title: string
  summary: string
  content: string
  coverUrl: string
  gmtModified: string
}
