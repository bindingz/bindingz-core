/*
 * Copyright (c) 2020 Connor Goulding
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.bindingz.source.code.provider.jsonschematopojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jsonschema2pojo.AllFileFilter;
import org.jsonschema2pojo.AnnotationStyle;
import org.jsonschema2pojo.Annotator;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.InclusionLevel;
import org.jsonschema2pojo.Language;
import org.jsonschema2pojo.NoopAnnotator;
import org.jsonschema2pojo.SourceSortOrder;
import org.jsonschema2pojo.SourceType;
import org.jsonschema2pojo.rules.RuleFactory;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonSchema2PojoConfiguration implements GenerationConfig {
    private File targetSourceDirectory;
    private File targetResourceDirectory;

    private String packageName;
    private String className;

    private boolean generateBuilders = false;
    private boolean useInnerClassBuilders = false;
    private boolean usePrimitives = false;
    private char[] propertyWordDelimiters = new char[]{'-', ' ', '_'};
    private boolean useLongIntegers = false;
    private boolean useBigIntegers = false;
    private boolean useDoubleNumbers = true;
    private boolean useBigDecimals = false;
    private boolean includeHashcodeAndEquals = true;
    private boolean includeConstructors = false;
    private boolean constructorsRequiredPropertiesOnly = false;
    private boolean includeToString = true;
    private String[] toStringExcludes = new String[]{};
    private AnnotationStyle annotationStyle = AnnotationStyle.JACKSON;
    private boolean useTitleAsClassname = false;
    private InclusionLevel inclusionLevel = InclusionLevel.NON_NULL;
    private Class<? extends Annotator> customAnnotator = NoopAnnotator.class;
    private Class<? extends RuleFactory> customRuleFactory = RuleFactory.class;
    private boolean includeJsr303Annotations = false;
    private boolean includeJsr305Annotations = false;
    private boolean useOptionalForGetters = false;
    private SourceType sourceType = SourceType.JSONSCHEMA;
    private String outputEncoding = "UTF-8";
    private boolean useJodaDates = false;
    private boolean useJodaLocalDates = false;
    private boolean useJodaLocalTimes = false;
    private String dateTimeType = null;
    private String dateType = null;
    private String timeType = null;
    private boolean parcelable = false;
    private boolean serializable = false;
    private FileFilter fileFilter = new AllFileFilter();
    private boolean initializeCollections = true;
    private String classNamePrefix = "";
    private String classNameSuffix = "";
    private String[] fileExtensions = new String[]{};
    private boolean includeAdditionalProperties = true;
    private boolean includeGetters = true;
    private boolean includeSetters = true;
    private String targetVersion = "1.8";
    private boolean includeDynamicAccessors = false;
    private boolean includeDynamicGetters = false;
    private boolean includeDynamicSetters = false;
    private boolean includeDynamicBuilders = false;
    private boolean formatDates = false;
    private boolean formatTimes = false;
    private boolean formatDateTimes = false;
    private String refFragmentPathDelimiters = "#/.";
    private SourceSortOrder sourceSortOrder = SourceSortOrder.OS;
    private Map<String, String> formatTypeMapping = Collections.emptyMap();
    private boolean removeOldOutput = false;
    private String customDatePattern;
    private String customTimePattern;
    private String customDateTimePattern;
    private Language targetLanguage = Language.JAVA;

    @Override
    public Iterator<URL> getSource() {
        try {
            return Arrays.asList(Paths.get(targetResourceDirectory.toString()).toUri().toURL()).iterator();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public File getTargetDirectory() {
        return targetSourceDirectory;
    }

    @Override
    public String getTargetPackage() {
        return packageName;
    }

    public void setAnnotationStyle(String style) {
        annotationStyle = AnnotationStyle.valueOf(style.toUpperCase());
    }

    public void setInclusionLevel(String level) {
        inclusionLevel = InclusionLevel.valueOf(level.toUpperCase());
    }

    public void setCustomAnnotator(String clazz) throws ClassNotFoundException {
        this.setCustomAnnotator(Class.forName(clazz, true, this.getClass().getClassLoader()));
    }

    public void setCustomAnnotator(Class clazz) {
        customAnnotator = clazz;
    }

    public void setCustomRuleFactory(String clazz) throws ClassNotFoundException {
        this.setCustomRuleFactory(Class.forName(clazz, true, this.getClass().getClassLoader()));
    }

    public void setCustomRuleFactory(Class clazz) {
        customRuleFactory = clazz;
    }

    public void setSourceType(String s) {
        sourceType = SourceType.valueOf(s.toUpperCase());
    }

    public void setSourceSortOrder(String sortOrder) {
        sourceSortOrder = SourceSortOrder.valueOf(sortOrder.toUpperCase());
    }

    public void setTargetLangauge(String language) {
        targetLanguage = Language.valueOf(language.toUpperCase());
    }

    public boolean isFormatDateTimes() {
        return formatDateTimes;
    }

    public File getTargetSourceDirectory() {
        return targetSourceDirectory;
    }

    public void setTargetSourceDirectory(File targetSourceDirectory) {
        this.targetSourceDirectory = targetSourceDirectory;
    }

    public File getTargetResourceDirectory() {
        return targetResourceDirectory;
    }

    public void setTargetResourceDirectory(File targetResourceDirectory) {
        this.targetResourceDirectory = targetResourceDirectory;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public boolean isGenerateBuilders() {
        return generateBuilders;
    }

    public void setGenerateBuilders(boolean generateBuilders) {
        this.generateBuilders = generateBuilders;
    }

    @Override
    public boolean isUseInnerClassBuilders() {
        return useInnerClassBuilders;
    }

    public void setUseInnerClassBuilders(boolean useInnerClassBuilders) {
        this.useInnerClassBuilders = useInnerClassBuilders;
    }

    @Override
    public boolean isUsePrimitives() {
        return usePrimitives;
    }

    public void setUsePrimitives(boolean usePrimitives) {
        this.usePrimitives = usePrimitives;
    }

    @Override
    public char[] getPropertyWordDelimiters() {
        return propertyWordDelimiters;
    }

    public void setPropertyWordDelimiters(char[] propertyWordDelimiters) {
        this.propertyWordDelimiters = propertyWordDelimiters;
    }

    @Override
    public boolean isUseLongIntegers() {
        return useLongIntegers;
    }

    public void setUseLongIntegers(boolean useLongIntegers) {
        this.useLongIntegers = useLongIntegers;
    }

    @Override
    public boolean isUseBigIntegers() {
        return useBigIntegers;
    }

    public void setUseBigIntegers(boolean useBigIntegers) {
        this.useBigIntegers = useBigIntegers;
    }

    @Override
    public boolean isUseDoubleNumbers() {
        return useDoubleNumbers;
    }

    public void setUseDoubleNumbers(boolean useDoubleNumbers) {
        this.useDoubleNumbers = useDoubleNumbers;
    }

    @Override
    public boolean isUseBigDecimals() {
        return useBigDecimals;
    }

    public void setUseBigDecimals(boolean useBigDecimals) {
        this.useBigDecimals = useBigDecimals;
    }

    @Override
    public boolean isIncludeHashcodeAndEquals() {
        return includeHashcodeAndEquals;
    }

    public void setIncludeHashcodeAndEquals(boolean includeHashcodeAndEquals) {
        this.includeHashcodeAndEquals = includeHashcodeAndEquals;
    }

    @Override
    public boolean isIncludeConstructors() {
        return includeConstructors;
    }

    public void setIncludeConstructors(boolean includeConstructors) {
        this.includeConstructors = includeConstructors;
    }

    @Override
    public boolean isConstructorsRequiredPropertiesOnly() {
        return constructorsRequiredPropertiesOnly;
    }

    public void setConstructorsRequiredPropertiesOnly(boolean constructorsRequiredPropertiesOnly) {
        this.constructorsRequiredPropertiesOnly = constructorsRequiredPropertiesOnly;
    }

    @Override
    public boolean isIncludeToString() {
        return includeToString;
    }

    public void setIncludeToString(boolean includeToString) {
        this.includeToString = includeToString;
    }

    @Override
    public String[] getToStringExcludes() {
        return toStringExcludes;
    }

    public void setToStringExcludes(String[] toStringExcludes) {
        this.toStringExcludes = toStringExcludes;
    }

    @Override
    public AnnotationStyle getAnnotationStyle() {
        return annotationStyle;
    }

    public void setAnnotationStyle(AnnotationStyle annotationStyle) {
        this.annotationStyle = annotationStyle;
    }

    @Override
    public boolean isUseTitleAsClassname() {
        return useTitleAsClassname;
    }

    public void setUseTitleAsClassname(boolean useTitleAsClassname) {
        this.useTitleAsClassname = useTitleAsClassname;
    }

    @Override
    public InclusionLevel getInclusionLevel() {
        return inclusionLevel;
    }

    public void setInclusionLevel(InclusionLevel inclusionLevel) {
        this.inclusionLevel = inclusionLevel;
    }

    @Override
    public Class<? extends Annotator> getCustomAnnotator() {
        return customAnnotator;
    }

    @Override
    public Class<? extends RuleFactory> getCustomRuleFactory() {
        return customRuleFactory;
    }

    @Override
    public boolean isIncludeJsr303Annotations() {
        return includeJsr303Annotations;
    }

    public void setIncludeJsr303Annotations(boolean includeJsr303Annotations) {
        this.includeJsr303Annotations = includeJsr303Annotations;
    }

    @Override
    public boolean isIncludeJsr305Annotations() {
        return includeJsr305Annotations;
    }

    public void setIncludeJsr305Annotations(boolean includeJsr305Annotations) {
        this.includeJsr305Annotations = includeJsr305Annotations;
    }

    @Override
    public boolean isUseOptionalForGetters() {
        return useOptionalForGetters;
    }

    public void setUseOptionalForGetters(boolean useOptionalForGetters) {
        this.useOptionalForGetters = useOptionalForGetters;
    }

    @Override
    public SourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(SourceType sourceType) {
        this.sourceType = sourceType;
    }

    @Override
    public String getOutputEncoding() {
        return outputEncoding;
    }

    public void setOutputEncoding(String outputEncoding) {
        this.outputEncoding = outputEncoding;
    }

    @Override
    public boolean isUseJodaDates() {
        return useJodaDates;
    }

    public void setUseJodaDates(boolean useJodaDates) {
        this.useJodaDates = useJodaDates;
    }

    @Override
    public boolean isUseJodaLocalDates() {
        return useJodaLocalDates;
    }

    public void setUseJodaLocalDates(boolean useJodaLocalDates) {
        this.useJodaLocalDates = useJodaLocalDates;
    }

    @Override
    public boolean isUseJodaLocalTimes() {
        return useJodaLocalTimes;
    }

    public void setUseJodaLocalTimes(boolean useJodaLocalTimes) {
        this.useJodaLocalTimes = useJodaLocalTimes;
    }

    @Override
    public String getDateTimeType() {
        return dateTimeType;
    }

    public void setDateTimeType(String dateTimeType) {
        this.dateTimeType = dateTimeType;
    }

    @Override
    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    @Override
    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    @Override
    public boolean isParcelable() {
        return parcelable;
    }

    public void setParcelable(boolean parcelable) {
        this.parcelable = parcelable;
    }

    @Override
    public boolean isSerializable() {
        return serializable;
    }

    public void setSerializable(boolean serializable) {
        this.serializable = serializable;
    }

    @Override
    public FileFilter getFileFilter() {
        return fileFilter;
    }

    public void setFileFilter(FileFilter fileFilter) {
        this.fileFilter = fileFilter;
    }

    @Override
    public boolean isInitializeCollections() {
        return initializeCollections;
    }

    public void setInitializeCollections(boolean initializeCollections) {
        this.initializeCollections = initializeCollections;
    }

    @Override
    public String getClassNamePrefix() {
        return classNamePrefix;
    }

    public void setClassNamePrefix(String classNamePrefix) {
        this.classNamePrefix = classNamePrefix;
    }

    @Override
    public String getClassNameSuffix() {
        return classNameSuffix;
    }

    public void setClassNameSuffix(String classNameSuffix) {
        this.classNameSuffix = classNameSuffix;
    }

    @Override
    public String[] getFileExtensions() {
        return fileExtensions;
    }

    public void setFileExtensions(String[] fileExtensions) {
        this.fileExtensions = fileExtensions;
    }

    @Override
    public boolean isIncludeAdditionalProperties() {
        return includeAdditionalProperties;
    }

    public void setIncludeAdditionalProperties(boolean includeAdditionalProperties) {
        this.includeAdditionalProperties = includeAdditionalProperties;
    }

    @Override
    public boolean isIncludeGetters() {
        return includeGetters;
    }

    public void setIncludeGetters(boolean includeGetters) {
        this.includeGetters = includeGetters;
    }

    @Override
    public boolean isIncludeSetters() {
        return includeSetters;
    }

    public void setIncludeSetters(boolean includeSetters) {
        this.includeSetters = includeSetters;
    }

    @Override
    public String getTargetVersion() {
        return targetVersion;
    }

    public void setTargetVersion(String targetVersion) {
        this.targetVersion = targetVersion;
    }

    @Override
    public boolean isIncludeDynamicAccessors() {
        return includeDynamicAccessors;
    }

    public void setIncludeDynamicAccessors(boolean includeDynamicAccessors) {
        this.includeDynamicAccessors = includeDynamicAccessors;
    }

    @Override
    public boolean isIncludeDynamicGetters() {
        return includeDynamicGetters;
    }

    public void setIncludeDynamicGetters(boolean includeDynamicGetters) {
        this.includeDynamicGetters = includeDynamicGetters;
    }

    @Override
    public boolean isIncludeDynamicSetters() {
        return includeDynamicSetters;
    }

    public void setIncludeDynamicSetters(boolean includeDynamicSetters) {
        this.includeDynamicSetters = includeDynamicSetters;
    }

    @Override
    public boolean isIncludeDynamicBuilders() {
        return includeDynamicBuilders;
    }

    public void setIncludeDynamicBuilders(boolean includeDynamicBuilders) {
        this.includeDynamicBuilders = includeDynamicBuilders;
    }

    @Override
    public boolean isFormatDates() {
        return formatDates;
    }

    public void setFormatDates(boolean formatDates) {
        this.formatDates = formatDates;
    }

    @Override
    public boolean isFormatTimes() {
        return formatTimes;
    }

    public void setFormatTimes(boolean formatTimes) {
        this.formatTimes = formatTimes;
    }

    public void setFormatDateTimes(boolean formatDateTimes) {
        this.formatDateTimes = formatDateTimes;
    }

    @Override
    public String getRefFragmentPathDelimiters() {
        return refFragmentPathDelimiters;
    }

    public void setRefFragmentPathDelimiters(String refFragmentPathDelimiters) {
        this.refFragmentPathDelimiters = refFragmentPathDelimiters;
    }

    @Override
    public SourceSortOrder getSourceSortOrder() {
        return sourceSortOrder;
    }

    public void setSourceSortOrder(SourceSortOrder sourceSortOrder) {
        this.sourceSortOrder = sourceSortOrder;
    }

    @Override
    public Map<String, String> getFormatTypeMapping() {
        return formatTypeMapping;
    }

    public void setFormatTypeMapping(Map<String, String> formatTypeMapping) {
        this.formatTypeMapping = formatTypeMapping;
    }

    @Override
    public boolean isRemoveOldOutput() {
        return removeOldOutput;
    }

    public void setRemoveOldOutput(boolean removeOldOutput) {
        this.removeOldOutput = removeOldOutput;
    }

    @Override
    public String getCustomDatePattern() {
        return customDatePattern;
    }

    public void setCustomDatePattern(String customDatePattern) {
        this.customDatePattern = customDatePattern;
    }

    @Override
    public String getCustomTimePattern() {
        return customTimePattern;
    }

    public void setCustomTimePattern(String customTimePattern) {
        this.customTimePattern = customTimePattern;
    }

    @Override
    public String getCustomDateTimePattern() {
        return customDateTimePattern;
    }

    public void setCustomDateTimePattern(String customDateTimePattern) {
        this.customDateTimePattern = customDateTimePattern;
    }

    @Override
    public Language getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(Language targetLanguage) {
        this.targetLanguage = targetLanguage;
    }
}
