package cc.abing.abstart.suite.system.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.IService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @description 封装DTO相关常用操作
 * @author ABing
 * @since 2026-08-25
 */
@Slf4j
public class DTOUtil {

	/**
	 * 获取DTOlist中元素字段的Set集合
	 * @param dtoList dtoList
	 * @param getter DTO::getter
	 * @param <T> DTO类型
	 * @param <R> DTO::getter 返回类型
	 * @return
	 */
	public static <T, R> Set<R> getFieldSet(List<T> dtoList, Function<? super T, ? extends R> getter) {
		return Misc.isEmpty(dtoList) ? Collections.emptySet()
				: dtoList.stream().map(getter).filter(Objects::nonNull).collect(Collectors.toSet());
	}

	/**
	 * 获取DTOlist中元素字段的Set集合
	 * @param dtoPage DTOPage
	 * @param getter DTO::getter
	 * @param <T> DTO类型
	 * @param <R> DTO::getter 返回类型
	 * @return
	 */
	public static <T, R> Set<R> getFieldSet(Page<T> dtoPage, Function<? super T, ? extends R> getter) {
		return getFieldSet(dtoPage.getRecords(), getter);
	}

	/**
	 * 连表获取名称Map <Index,Target_Value>
	 * @param idSet IdSet
	 * @param nameService 服务实现类Bean实例
	 * @param getterK NameDTO::getter1 => Map Key
	 * @param getterV NameDTO::getter2 => Map Value
	 * @param valueNotNullPredicate (o) -> Misc.isNotEmpty(o.getName())
	 * @param <T> NameDTO
	 * @param <S> Set集合中元素类型
	 * @param <R2> Map Key类型
	 * @param <R3> Map Value类型
	 * @return
	 */
	public static <T, S extends Serializable, R2, R3> Map<R2, R3> getNameMap(Set<S> idSet, IService<T> nameService,
																			 Function<? super T, ? extends R2> getterK, Function<? super T, ? extends R3> getterV,
																			 Predicate<? super T> valueNotNullPredicate) {
		return Misc.isEmpty(idSet) ? Collections.emptyMap() : nameService.listByIds(idSet).stream()
				.filter(valueNotNullPredicate).collect(Collectors.toMap(getterK, getterV, (o1, o2) -> o1));
	}

	/**
	 * 连表获取名称Map <Index,Target_Value>
	 * @param dtoList DTOList
	 * @param nameService 服务实现类Bean实例
	 * @param DTOGetter DTO::getter
	 * @param getterK NameDTO::getter1 => Map Key
	 * @param getterV NameDTO::getter2 => Map Value
	 * @param valueNotNullPredicate (o) -> Misc.isNotEmpty(o.getName())
	 * @param <T1> DTO
	 * @param <T2> NameDTO
	 * @param <R1> DTO::getter 返回类型
	 * @param <R2> Map Key类型
	 * @param <R3> Map Value类型
	 * @return
	 */
	public static <T1, T2, R1 extends Serializable, R2, R3> Map<R2, R3> getNameMap(List<T1> dtoList,
																				   IService<T2> nameService, Function<? super T1, ? extends R1> DTOGetter,
																				   Function<? super T2, ? extends R2> getterK, Function<? super T2, ? extends R3> getterV,
																				   Predicate<? super T2> valueNotNullPredicate) {
		Set<R1> idSet = getFieldSet(dtoList, DTOGetter);
		return Misc.isEmpty(idSet) ? Collections.emptyMap() : nameService.listByIds(idSet).stream()
				.filter(valueNotNullPredicate).collect(Collectors.toMap(getterK, getterV, (o1, o2) -> o1));
	}

	/**
	 * 连表获取名称Map <Index,Target_Value>
	 * @param dtoPage DTOPage
	 * @param nameService 服务实现类Bean实例
	 * @param DTOGetter DTO::getter
	 * @param getterK NameDTO::getter1 => Map Key
	 * @param getterV NameDTO::getter2 => Map Value
	 * @param valueNotNullPredicate (o) -> Misc.isNotEmpty(o.getName())
	 * @param <T1> DTO
	 * @param <T2> NameDTO
	 * @param <R1> DTO::getter 返回类型
	 * @param <R2> Map Key类型
	 * @param <R3> Map Value类型
	 * @return
	 */
	public static <T1, T2, R1 extends Serializable, R2, R3> Map<R2, R3> getNameMap(Page<T1> dtoPage,
																				   IService<T2> nameService, Function<? super T1, ? extends R1> DTOGetter,
																				   Function<? super T2, ? extends R2> getterK, Function<? super T2, ? extends R3> getterV,
																				   Predicate<? super T2> valueNotNullPredicate) {
		Set<R1> idSet = getFieldSet(dtoPage, DTOGetter);
		return Misc.isEmpty(idSet) ? Collections.emptyMap() : nameService.listByIds(idSet).stream()
				.filter(valueNotNullPredicate).collect(Collectors.toMap(getterK, getterV, (o1, o2) -> o1));
	}

	/**
	 * 连表获取名称Map <Index,DTO>
	 * @param dtoPage DTOPage
	 * @param nameService 服务实现类Bean实例
	 * @param DTOGetter DTO::getter
	 * @param getterK NameDTO::getter1 => Map Key
	 * @param <T1> DTO
	 * @param <T2> NameDTO
	 * @param <R1> DTO::getter 返回类型
	 * @param <R2> Map Key类型
	 * @return
	 */
	public static <T1, T2, R1 extends Serializable, R2> Map<R2, T2> getNameDTOMap(Page<T1> dtoPage,
																				  IService<T2> nameService, Function<? super T1, ? extends R1> DTOGetter,
																				  Function<? super T2, ? extends R2> getterK) {
		Set<R1> idSet = getFieldSet(dtoPage, DTOGetter);
		return Misc.isEmpty(idSet) ? Collections.emptyMap() : nameService.listByIds(idSet).stream()
				.collect(Collectors.toMap(getterK, Function.identity(), (o1, o2) -> o1));
	}

	/**
	 * 转换VOList
	 * @param dtoList DTOList
	 * @param VOclazz VO.class
	 * @param improver 改造函数
	 * @param <T> DTO
	 * @param <R> VO
	 * @return
	 */
	public static <T, R> List<R> VOList(List<T> dtoList, Class<R> VOclazz, BiConsumer<T, R> improver) {
		List<R> retList = new ArrayList<>();
		for (T t : dtoList) {
			R r = null;
			try {
				r = VOclazz.newInstance();
			}
			catch (Exception e) {
				log.error("VOclazz调用无参构造函数失败", e);
				if (e instanceof InstantiationException) {
					throw new RuntimeException("InstantiationException");
				}
				else if (e instanceof IllegalAccessException) {
					throw new RuntimeException("IllegalAccessException");
				}
			}
			assert r != null;
			BeanUtils.copyProperties(t, r);
			if (improver != null) {
				improver.accept(t, r);
			}
			retList.add(r);
		}
		return retList;
	}

	/**
	 * 转换空VOPage
	 * @param dtoPage DTOPage
	 * @param VOclazz VO.class
	 * @param <T> DTO
	 * @param <R> VO
	 * @return
	 */
	public static <T, R> Page<R> VOPage(Page<T> dtoPage, Class<R> VOclazz) {
		return VOPage(dtoPage, VOclazz, null);
	}

	/**
	 * 转换VOPage
	 * @param dtoPage DTOPage
	 * @param VOclazz VO.class
	 * @param improver 改造函数
	 * @param <T> DTO
	 * @param <R> VO
	 * @return
	 */
	public static <T, R> Page<R> VOPage(Page<T> dtoPage, Class<R> VOclazz, BiConsumer<T, R> improver) {
		Page<R> resultPage = new Page<>();
		BeanUtils.copyProperties(dtoPage, resultPage);
		resultPage.setRecords(VOList(dtoPage.getRecords(), VOclazz, improver));
		return resultPage;
	}

}
