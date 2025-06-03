import {request} from "@/API/request";
import {HeroListReq} from "@/API/forms/hero";

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
