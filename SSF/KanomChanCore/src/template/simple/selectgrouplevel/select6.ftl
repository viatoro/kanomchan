<#--
/*
 * $Id$
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
-->
<#setting number_format="#.#####">
<script>
	<#include "css/jquery.scrollbar.css">
	<#include "js/jquery.scrollbar.js">
</script>
<style>

	.select6-wrapper{
	    width:  100%;
	    position: relative;
	}
	.select6-input-box{
		border: 1px solid #CCC;
	}
	.select6-input-wrapper{
	
	}
	.select6-input{
		background-color: #FFF;
	    cursor: text;
	    min-height: 32px;
	    width: 100%;
	    display: inline-block;
	    margin-bottom: -5px;
	    padding-right: 5px;
	}
	.select6-input-item{
	    position: relative;
	    float: left;
	    padding: 0px 5px;
	    background-color: #469ED4;
	    color: #FFF;
	    margin: 5px 0px 5px 5px;
	    border: 1px solid #415D8E;
	    border-radius: 4px;
	    cursor: default;
	}
	.select6-input-box-cursor{
	    padding: 7px;
	    margin: 0;
	    border: 0px;
	    outline: none;
	    width: 16px;
	}
	.select6-input-item-label{
	    float: left;
	    font-size: 14px;
	    padding-right: 20px;
	}
	.select6-input-remove{
	    padding: 0px 3px;
	    background-color: #FFF;
	    color: #888;
	    margin-left: 7px;
	    margin-top: 3px;
	    border-radius: 4px;
	    cursor: pointer;
	    font-size: 9px;
	    position: absolute;
	    right: 5px;
	}
	.select6-select-container{
	    width: 100%;
	    background-color: #FFF;
	    border: 1px solid #CCC;
	    padding: 0px;
	    max-height: 500px;
	    position: absolute;
	    z-index: 100;
	    margin-top: -1px;
	    display: none;
	
	}
	.select6-select-item{
	    width: 100%;
	    padding: 10px 25px;
	    position: relative;
	    color: #888;
	    cursor: pointer;
	    transition: background-color 0.2s ease;
	}
	.select6-select-item label{
	    cursor: pointer;
	}
	.select6-select-item.active, .select6-select-item.active.selected{
		background-color: #C9E9FF;
	    border-left: 3px solid #FF8D57;
	}
	.select6-select-item.selected:hover{
		background-color: #DDF1FF;
	}
	.select6-select-item.active:hover{
		background-color: #C9E9FF;
	}
	.select6-select-item.selected{
		background-color: #DDF1FF;
	}
	.sub .select6-select-item:hover{
		background-color: #93D3FF;
	}
	.select6-select-item:hover{
		background-color: #E5E5E5;
	}
	.select6-select-col{
		width: 50%;
		float: left;
		height: 250px;
	}
	.select6-select-col.sub{
		background-color: #C9E9FF;
	}
	.select6-select-col.sub.disabled{
		background-color: #F9F9F9 !important;
	}
	.select6-select-col.sub.disabled .select6-select-item label{
		color: #DDD !important;
	}
	.select6-select-col.sub.disabled .check-box{
		background-color: #DDD;
	}
	.icon-right{
		position: absolute;
		right: 13px;
		top: 13px;
	}
	.select6-scroll{
	    max-height: 250px;
	    height: 100%;
	}
	.select6-scroll::-webkit-scrollbar {
		display: none;
	}
	.check-box{
	    position: absolute;
	    left: 5px;
	    background-color: #FFF;
	    width: 15px;
	    height: 15px;
	    border-radius: 4px;
	    border: 1px solid #BBB;
	    cursor: pointer;
	}
	.check-box.isChecked{
		font-family: FontAwesome;
	}
	.check-box.isChecked:before{
		content: "\f00c";
	    top: -1px;
	    left: 1px;
	    position: absolute;
	}
	.select6-input-sub-item-wrapper{
		position: absolute;
	    min-width: calc(100% + 2px);
	    width: 250px;
	    background-color: #469ED4;
	    left: -1px;
	    bottom: -1px;
	    border-radius: 4px;
	    border: 1px solid #415D8E;
	    padding: 5px;
	    z-index: 10000;
	    display: none;
	}
	.select6-input-sub-item-label{
	    background-color: #3577A0;
	    margin-bottom: 5px;
	    padding: 2px 5px;
	    border-radius: 4px;
	}
	.select6-input-sub-item-label:last-of-type{
	    margin-bottom: 0px;
	}
	.select6_${parameters.id} .radio-label{
		color: #888;
		font-size: 12px;
	}
	.item_profi_radio_${parameters.id}{
 		display:none; 
	}
	.select6_${parameters.id} label em{
		font-weight:bold;
  		font-style:normal;
  		 background:#ff6;
	}
	.select6_${parameters.id} .spinner-div{
		text-align: center;
		margin: 5px;
	}
	.title-container div div{
		padding-top: 5px;
		padding-left: 10px;
	}
	.title-container div div label{
		font-size: 12px;
	    color: #888;
	    font-weight: bold;
	}
	.select6_${parameters.id} .spinner-search-div{
		display: none;
	    position: absolute;
	    right: 20px;
	    bottom: 0px;
	}
</style>
<div class="select6-wrapper multiple select6_${parameters.id}" onclick="clickSearch_${parameters.id}(event)">
	<div class="select6-input-box">
		<div class="select6-input-box">
			<div class="select6-input-wrapper">
				<div class="select6-input" id="select6-input-${parameters.id}" onclick="openselect6_${parameters.id}(event,$(this))">
					<#if parameters.nameList??>
						<@s.iterator value="stack.findValue(parameters.nameList)" status="status">
							<div class="select6-input-item" onclick="inputItemClick_${parameters.id}(event,$(this))">
								<div class="select6-input-item-label">
								 	<@s.property value=(parameters.nameValue)/>
								</div>
								<input type="hidden" class="id_${parameters.id}" name="${parameters.name}[<@s.property value="#status.index"/>].${parameters.nameKey}" value="<@s.property value=(parameters.nameKey)/>"> 
								<input type="hidden" class="id_${parameters.id}_parent" name="${parameters.name}[<@s.property value="#status.index"/>].${parameters.nameParentKey}" value="<@s.property value=(parameters.nameParentKey)/>">
								<input type="hidden" name="${parameters.name}[<@s.property value="#status.index"/>].<#if parameters.nameStatus??>${parameters.nameStatus}<#else>status</#if>" value="<#if parameters.nameStatusInitKey??>${parameters.nameStatusInitKey}<#else>A</#if>">
								<div class="select6-input-remove" onclick="deleteInputItem_${parameters.id}(event,$(this).parent());"><i class="fa fa-times"></i></div>
							</div>
							<input type="hidden" name="item_count_${parameters.id}"/>
							<#if parameters.nameToSaveKey??>
								<input type="hidden" class="id_${parameters.id}_toSave" name="${parameters.name}[<@s.property value="#status.index"/>].${parameters.nameToSaveKey}" value="<@s.property value=(parameters.nameToSaveKey)/>"> 
							</#if>
							<input type="hidden" name="__pushdataonremove_${parameters.name}[<@s.property value="#status.index"/>].<#if parameters.nameStatus??>${parameters.nameStatus}<#else>status</#if>" value="I">
						</@s.iterator>
					</#if>
					<div class="clone-input-${parameters.id}" style="display: none;">
						<div class="select6-input-item" onclick="inputItemClick_${parameters.id}(event,$(this))">
							<div class="select6-input-item-label" >
								{0}
							</div>
							<input type="hidden" class="id_${parameters.id}" name="${parameters.name}[{3}].${parameters.nameKey}" value="{1}"> 
							<input type="hidden" class="id_${parameters.id}_parent" name="${parameters.name}[{3}].${parameters.nameParentKey}" value="{2}">
							<input type="hidden" name="${parameters.name}[{3}].<#if parameters.nameStatus??>${parameters.nameStatus}<#else>status</#if>" value="<#if parameters.nameStatusInitKey??>${parameters.nameStatusInitKey}<#else>A</#if>">
							<div class="select6-input-remove" onclick="deleteInputItem_${parameters.id}(event,$(this).parent());"><i class="fa fa-times"></i></div>
						</div>
						<input type="hidden" name="item_count_${parameters.id}"/>
						<input type="hidden" name="__pushdataonremove_${parameters.name}[{3}].<#if parameters.nameStatus??>${parameters.nameStatus}<#else>status</#if>" value="I">
					</div>
				</div>
			</div>
		</div>
		<div class="select6-select-container" style="display: none;">
			<div class="row">
				<div class="col-md-12">
					<input type="text" name="keyWord" id="keyWordSearch_${parameters.id}" class="form-control" placeholder="<@s.text name="COMMON_SEARCH"/>" onclick="clickSearch_${parameters.id}(event)">
					<div class="spinner-search-div">
						<i class="fa fa-spinner fa-2x fa-spin"></i>
					</div>
				</div> 
			</div>
			<div class="row title-container">
				<div class="col-xs-6">
					<div><label> <#if parameters.groupListLabel??> <@s.text name="${parameters.groupListLabel}"/> <#else><@s.text name="COMMON_GROUP"/> </#if></label></div>
				</div>
				<div class="col-xs-6">
					<div><label><#if parameters.subNameLabel??> <@s.text name="${parameters.subNameLabel}"/> <#else><@s.text name="COMMON_SUP_GROUP"/> </#if> </label></div>
				</div>
			</div>
			<div class="select6-select-col group">
				<div class="select6-scroll mCustomScrollbar" data-mcs-theme="minimal-dark">
					<div class="select6-select-item all-group active" id="select6-select-item-${parameters.id}-0" onclick="groupClick_${parameters.id}(event,$(this))">
						<input type="hidden" class="item_id_${parameters.id}" value="0">
						<label style="font-size: 12px;"><@s.text name="COMMON_ALL_GROUP"/></label> 
						<i class="fa fa-chevron-right icon-right" style="color: #BBB"></i>
					</div>
					<@s.iterator value="stack.findValue(parameters.groupList)">
						<div class="select6-select-item" id="select6-select-item-${parameters.id}-<#if parameters.groupListKey??><@s.property value="${parameters.groupListKey}"/></#if>" onclick="groupClick_${parameters.id}(event,$(this))">
							<input type="hidden" class="item_id_${parameters.id}" value="<#if parameters.groupListKey??><@s.property value="${parameters.groupListKey}"/></#if>">
							<input type="hidden" class="item_name_${parameters.id}" value="<#if parameters.groupListValue??><@s.property value="${parameters.groupListValue}"/></#if>">
							<label style="font-size: 12px;"><#if parameters.groupListValue??><@s.property value="${parameters.groupListValue}"/></#if> </label> 
							<i class="fa fa-chevron-right icon-right" style="color: #BBB"></i>
						</div>
					</@s.iterator>
				</div>
			</div>
			<div class="select6-select-col sub">
				<div class="select6-scroll mCustomScrollbar" data-mcs-theme="minimal-dark" >
					<div class="clone${parameters.id}" style="display: none">
						<div class="select6-select-item" id="select6-select-item-sub-${parameters.id}-{0}" onclick="click_${parameters.id}(event,$(this));">
							<div class="check-box" onclick="<#if parameters.singleSelect??>
								<#if stack.findValue(parameters.singleSelect)>
									checkboxClickSigle_${parameters.id}(event,$(this).parent()); 
								<#else>
									checkboxClick_${parameters.id}(event,$(this).parent());
								</#if>
								<#else>
									checkboxClick_${parameters.id}(event,$(this).parent());
								</#if>
							">
								<input type="hidden" class="item_id_${parameters.id}" value="{0}">
								<input type="hidden" name="item_parent_id_${parameters.id}" value="{2}">
								<input type="hidden" class="item_name_${parameters.id}" value="{3}">
							</div>
							<label style="font-size: 12px;">{1}</label>
						</div>
					</div>
					<div class="spinner-div">
						<i class="fa fa-spinner fa-2x fa-spin"></i>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	var currentPage_${parameters.id};
	var  isLastPage_${parameters.id} = false;
	scrollEnd_${parameters.id} = function(){
		currentPage_${parameters.id}++;
		var idParent = $('.select6_${parameters.id} .select6-select-item.active').children('.item_id_${parameters.id}').val();
		var keyWord = $("#keyWordSearch_${parameters.id}").val();
		
		var urlSub;
		if( idParent == 0){
			urlSub = '${parameters.subAllGroupUrl}';
		}
		else{
			urlSub = '${parameters.subUrl}';
		}
		
		if(!isLastPage_${parameters.id}){
			$.ajax({
				type : "POST",
				url : urlSub,
				data : {"idParent": idParent,
						"keyWord": keyWord,
						"currentPage": currentPage_${parameters.id}},
				cache : false,
				success : function(jsonResponse) {
					if (typeof jsonResponse != 'undefined' && jsonResponse.success && jsonResponse != null && jsonResponse.result != null){
						var rowsPerPage = jsonResponse.pagingBean.rowsPerPage;
						var totalRows = jsonResponse.pagingBean.totalRows;
						var totalPage = totalRows/rowsPerPage;
						if(totalRows%rowsPerPage > 0){
							totalPage++;
						}
						if(currentPage_${parameters.id}+1 > totalPage){
							isLastPage_${parameters.id} = true;
							$(".select6_${parameters.id} .spinner-div").hide();
						}
						if(totalRows == 0 ){
							$(".select6_${parameters.id} .spinner-div").hide();
						}
						
						$.each( jsonResponse.result<#if parameters.subName??>.${parameters.subName}</#if>, function( key, val ) {
							var data = $(".clone${parameters.id}").html();
							var name ;
							if(keyWord != null && keyWord != "" ){
								var text = val.${parameters.subNameValue};
								var matchStart = text.toLowerCase().indexOf(keyWord.toLowerCase());
								if (matchStart >= 0){
									name = highlightText(text,matchStart,keyWord);
								}
								else{
									name = text;
								}
							}
							else{
								name = val.${parameters.subNameValue};
							}
							
							$('.clone${parameters.id}').before(data.format(
								val.${parameters.subNameKey}, // {0}
								name, // {1}
								val.${parameters.subNameParentKey},
								val.${parameters.subNameValue}
							));
						});
						
						
						setInputToItemList_${parameters.id}();
						if (typeof func != 'undefined' && func != null && typeof idItem != 'undefined' && idItem != null){
							func(idItem);
						}
					}
				}
			});
		}
	}

	$(document).on('click',function() {
		$('.select6-select-container').hide();
	});
	var openselect6_${parameters.id} = function(event,element) {
		$('.select6_${parameters.id} .select6-select-container').not(element.parent().parent().parent().children('.select6-select-container')).hide();
		element.parent().parent().parent().children('.select6-select-container').toggle();
		event.stopPropagation();
	};
	$(document).ready(function(){
		$(".select6_${parameters.id} .select6-select-col.group .select6-scroll").mCustomScrollbar();
		$(".select6_${parameters.id} .select6-select-col.sub .select6-scroll").mCustomScrollbar({
				callbacks : {onScroll:function(){
					if(this.mcs.topPct > 90	){
						scrollEnd_${parameters.id}();
					}
				}}
		});
		$('.select6_${parameters.id} .scroll-element.scroll-y').hide(0);
		load_item_${parameters.id}($('#select6-select-item-${parameters.id}-0'),null);

	});
	
	clickSearch_${parameters.id} = function(event){
		event.stopPropagation();
	}
	
	checkboxClick_${parameters.id} = function(event,e){
		event.stopPropagation();
		var checkBox = e.children('.check-box');
		if(checkBox.hasClass('isChecked')){
			checkBox.removeClass('isChecked'); 
			$('#select6-input-${parameters.id} .select6-input-item').has("input.id_${parameters.id}[value="+ $(checkBox).children(".item_id_${parameters.id}").val() +"]").remove();
		}
		else{
			checkBox.addClass('isChecked');
			setItemToInput_${parameters.id}(e);
		}
	}
	
	<#if parameters.singleSelect??>
	<#if stack.findValue(parameters.singleSelect)>
	checkboxClickSigle_${parameters.id} = function(event,e){
		event.stopPropagation();
		var checkBox = e.children('.check-box');
		if(checkBox.hasClass('isChecked')){
			checkBox.removeClass('isChecked'); 
			$('#select6-input-${parameters.id} .select6-input-item').has("input.id_${parameters.id}[value="+ $(checkBox).children(".item_id_${parameters.id}").val() +"]").remove();
		}
		else{
			checkBox.addClass('isChecked');
			$("#select6-input-${parameters.id} > div:not(.clone-input-${parameters.id})").remove();
			setItemToInput_${parameters.id}(e);
			$(".select6_${parameters.id} .select6-select-item .check-box").removeClass("isChecked");
			setInputToItemList_${parameters.id}();
		}
	}
	</#if>
	</#if>
	
	groupClick_${parameters.id} = function(event,e){
		event.stopPropagation();
		
		load_item_${parameters.id}(e, null);
	}
	
	load_item_${parameters.id} = function(e,func,idItem){
		if(e.attr("id") != $('.select6_${parameters.id} .select6-select-item.active').attr("id")){
			$('.select6_${parameters.id} .select6-select-item').removeClass('active');
		}
		e.addClass("active");
		
		$('.select6_${parameters.id} .select6-select-col.sub .select6-scroll div:not(.clone${parameters.id})>.select6-select-item').remove();
		
		e.addClass("active");
		var idParent = e.children('.item_id_${parameters.id}').val();
		var keyWord = $("#keyWordSearch_${parameters.id}").val();
		currentPage_${parameters.id} = 1;
		isLastPage_${parameters.id} = false;
		$(".select6_${parameters.id} .spinner-div").show();
		
		var urlSub;
		if( idParent == 0){
			urlSub = '${parameters.subAllGroupUrl}';
		}
		else{
			urlSub = '${parameters.subUrl}';
		}
		
		$.ajax({
			type : "POST",
			url : urlSub,
			data : {"idParent": idParent,
					"keyWord": keyWord,
					"currentPage": currentPage_${parameters.id}},
			cache : false,
			success : function(jsonResponse) {
				if (typeof jsonResponse != 'undefined' && jsonResponse.success && jsonResponse != null && jsonResponse.result != null && jsonResponse.result<#if parameters.subName??>.${parameters.subName}</#if>.length > 0){
					$.each( jsonResponse.result<#if parameters.subName??>.${parameters.subName}</#if>, function( key, val ) {
						var rowsPerPage = jsonResponse.pagingBean.rowsPerPage;
						var totalRows = jsonResponse.pagingBean.totalRows;
						var totalPage = totalRows/rowsPerPage;
						if(totalRows % rowsPerPage > 0){
							totalPage++;
						}
						
						if(totalRows <= rowsPerPage || currentPage_${parameters.id}+1 > totalPage || totalRows == 0){
							isLastPage_${parameters.id} = true;
							$(".select6_${parameters.id} .spinner-div").hide();
						}
						
						var data = $(".clone${parameters.id}").html();
						var name ;
						if(keyWord != null && keyWord != "" ){
							var text = val.${parameters.subNameValue};
							var matchStart = text.toLowerCase().indexOf(keyWord.toLowerCase());
							if (matchStart >= 0){
								name = highlightText(text,matchStart,keyWord);
							}
							else{
								name = text;
							}
						}
						else{
							name = val.${parameters.subNameValue};
						}
						
						$('.clone${parameters.id}').before(data.format(
							val.${parameters.subNameKey}, // {0}
							name, // {1}
							val.${parameters.subNameParentKey},
							val.${parameters.subNameValue}
						));
						
					});
					
					
					setInputToItemList_${parameters.id}();
					if (typeof func != 'undefined' && func != null && typeof idItem != 'undefined' && idItem != null){
						func(idItem);
					}
				}
				else{
					$(".select6_${parameters.id} .spinner-div").hide();
				}
			}
			
		});
	}
	
	setInputToItemList_${parameters.id} = function(){
		
		$('#select6-input-${parameters.id}').children('.select6-input-item').each(function(key,value){
		   	var idInputSkill = $(value).children('.id_${parameters.id}').val();
		   
		 	var checkBox = $("#select6-select-item-sub-${parameters.id}-"+idInputSkill).children('.check-box');
			if(!checkBox.hasClass('isChecked')){
				checkBox.addClass('isChecked');
			}
			  
			var idRadio = $(value).children('.id_radio').val();
			$("#select6-select-item-sub-${parameters.id}-"+idInputSkill+" input[type='radio']").filter('[value="'+idRadio+'"]').attr('checked', true);
		});
	}
	
	click_${parameters.id} = function(event, e){
		event.stopPropagation();
		<#if parameters.singleSelect??>
		<#if stack.findValue(parameters.singleSelect)>
			checkboxClickSigle_${parameters.id}(event, e);
			<#else>
			checkboxClick_${parameters.id}(event, e);
		</#if>
		<#else>
			checkboxClick_${parameters.id}(event, e);
		</#if>
		
	}
	
	setItemToInput_${parameters.id} = function(e){
		var id = e.children('.check-box').children('.item_id_${parameters.id}').val();
		var idParent = e.children('.check-box').children('[name="item_parent_id_${parameters.id}"]').val();
		
		var name = e.children('label').text();
		
		var isDup = false;
		$('#select6-input-${parameters.id}').children('.select6-input-item').each(function(key,value){
		   var idInputSkill = $(value).children('.id_${parameters.id}').val();
		   if(idInputSkill == id){
			   isDup = true;
			   $(value).children('.select6-input-item-label').text(name);
		   }
		});
		
		if(!isDup){
			var data = $(".clone-input-${parameters.id}").html();
			$('.clone-input-${parameters.id}').before(data.format(
				name, // {0}
				id,
				idParent,
				$(".select6_${parameters.id} div:not(.clone-input-${parameters.id})>input[name='item_count_${parameters.id}'] ").length
			));
		}
		
	}
		
	deleteInputItem_${parameters.id} = function(event,e){
		event.stopPropagation();
		var id_input_item = e.children('.id_${parameters.id}').val();
		$("#select6-select-item-sub-${parameters.id}-"+ id_input_item+ " .check-box").removeClass("isChecked");
		$("#select6-select-item-sub-${parameters.id}-"+ id_input_item+ " input[type='radio']").attr("checked" , false);
		e.remove();
	}
	
	inputItemClick_${parameters.id} = function(event,e){
		event.stopPropagation();
		var idActiveParent = $('.select6-select-item.active').children(".item_id_${parameters.id}").val();
		var id${parameters.id} = $(e).find(".id_${parameters.id}").val();
		var idGroup = $(e).find(".id_${parameters.id}_parent").val();
		$('#keyWordSearch_${parameters.id}').val("");
		load_item_${parameters.id}($('#select6-select-item-${parameters.id}-'+idGroup),autoFocus_${parameters.id},id${parameters.id});
	}
	
	autoFocus_${parameters.id} = function(id){
		$("#select6-select-item-sub-${parameters.id}-"+id).children(".item_profi_radio_${parameters.id}").show();
	}
	
	function keyWordChange_${parameters.id}(){
		var keyWord = $('#keyWordSearch_${parameters.id}').val();
		 $(".select6_${parameters.id} .spinner-search-div").hide();
		if(keyWord === ""){
			$(".select6_${parameters.id} .select6-select-col.group .select6-select-item").each(function(key,value){
				var text = $(value).children(".item_name_${parameters.id}").val();
				$(value).children("label").html(text);
			}).show();
			load_item_${parameters.id}($(".select6_${parameters.id} .select6-select-col.group .select6-select-item.active"), null);
		}
		else{
			$.ajax({
				type : "POST",
				url : '${parameters.searchGroupUrl}',
				data : { "keyWord": keyWord},
				cache : false,
				success : function(jsonResponse) {
					$(".select6_${parameters.id} .select6-select-col.group .select6-select-item:not(.all-group)").each(function(key,value){
						$(value).children("label").empty();
						var text = $(value).children(".item_name_${parameters.id}").val();
						var matchStart = text.toLowerCase().indexOf(keyWord.toLowerCase());
						if (matchStart >= 0){
							$(value).children("label").html(highlightText(text,matchStart,keyWord));
							$(value).show();
						}
						else{
							$(value).children("label").html(text);
							if($(value).hasClass('active')){
								$(value).show();
							}
							else{
								$(value).hide();
							}
						}
					});
					
					if (typeof jsonResponse != 'undefined' && jsonResponse.success && jsonResponse != null && jsonResponse.result != null){
						$.each( jsonResponse.result, function( key, val ) {
							$('#select6-select-item-${parameters.id}-'+ val.<#if parameters.searchGroupUrlKey??>${parameters.searchGroupUrlKey}<#else>${parameters.groupListKey}</#if>).show();
						});
					}
					

					load_item_${parameters.id}($(".select6_${parameters.id} .select6-select-item.active"), null);
					
				}
			});
		}
	}
	
	highlightText = function(text,matchStart, keyWord){
		var matchEnd = matchStart + keyWord.length - 1;
		var beforeMatch = text.slice(0, matchStart);
        var matchText = text.slice(matchStart, matchEnd + 1);
        var afterMatch = text.slice(matchEnd + 1);
        return beforeMatch + "<em>" + matchText + "</em>" + afterMatch;
	}
	
	var delay = (function(){
		  var timer = 0;
		  return function(callback, ms){
		    clearTimeout(timer);
		    timer = setTimeout(callback, ms);
		  };
	})();
	
	$('#keyWordSearch_${parameters.id}').keyup(function() {
	    delay(keyWordChange_${parameters.id} , 1000 );
	    $(".select6_${parameters.id} .spinner-search-div").show();
	});
	
</script>