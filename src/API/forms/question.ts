export interface KnowledgeItem {
  id: number
  question: string
  optionA: string
  optionB: string
  optionC: string
  optionD: string
  difficulty: number
  done: number
  correct: boolean
  choice: number
  answer: number
  explanation: string
  recordId: number
}
