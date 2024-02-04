import request from '@/utils/request'

export function upload(data) {
  return request({
    url: 'api/studioImage/upload',
    method: 'post',
    data
  })
}
export function add(data) {
  return request({
    url: 'api/studioImage',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/studioImage/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/studioImage',
    method: 'put',
    data
  })
}
// export function getAxis(data) {
//   return request({
//     url: 'api/studioImage',
//     method: 'put',
//     data
//   })
// }

export default { add, edit, del }
