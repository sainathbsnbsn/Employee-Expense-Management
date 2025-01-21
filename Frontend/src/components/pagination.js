import React, { useEffect, useState } from 'react';
import Icon from '@mdi/react';
import { mdiBellOutline, mdiLanPending } from '@mdi/js';
import 'bootstrap/dist/css/bootstrap.min.css';

export const Pagination = ({getPageNo,pageMax}) =>{
    const [page,setPage] = useState(1)

    useEffect(()=>{
      getPageNo(page)
    },[page])



    return (
        <>
       
<nav aria-label="Page navigation example">
  <ul class="pagination" style={{cursor:'pointer'}}>
    <li class="page-item" onClick={()=>{setPage((page>1)?(page-1):(1))}} disabled={page==1}>
      <a class="page-link" aria-label="Previous">
        <span aria-hidden="true">&laquo; </span>
      </a>
    </li>
    <li class="page-item" onClick={()=>{setPage((page<pageMax)?(page+1):(pageMax))}}><a class="page-link" >{page+1}</a></li>
    <li class="page-item" onClick={()=>{setPage((page+2<pageMax)?(page+2):(pageMax))}}><a class="page-link">{page+2}</a></li>
    <li class="page-item" onClick={()=>{setPage((page+3<pageMax)?(page+3):(pageMax))}}><a class="page-link" >{page+3}</a></li>
    <li class="page-item">
      <a class="page-link" aria-label="Next" onClick={()=>{setPage((page<pageMax)?(page+1):(pageMax))}} disabled={page==pageMax}>
        <span aria-hidden="true">&raquo;</span>
      </a>
    </li>
  </ul>
</nav>

        </>
    )
}