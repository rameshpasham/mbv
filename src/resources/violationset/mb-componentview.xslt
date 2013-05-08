<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
	version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output
		method="html"
		encoding="UTF-8"
		doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN"
		doctype-system="http://www.w3.org/TR/html4/loose.dtd"
		indent="yes" />
	
	<xsl:template
		name="message">
		<xsl:value-of
			disable-output-escaping="yes"
			select="description" />
	</xsl:template>

	<xsl:template
		name="priorityDiv">
		<xsl:if
			test="@priority = 1">
			p1
		</xsl:if>
		<xsl:if
			test="@priority = 2">
			p2
		</xsl:if>
		<xsl:if
			test="@priority = 3">
			p3
		</xsl:if>
		<xsl:if
			test="@priority = 4">
			p4
		</xsl:if>
	</xsl:template>

	<xsl:template
		name="timestamp">
		<xsl:value-of
			select="substring-before(//pmd/@timestamp, 'T')" />
		-
		<xsl:value-of
			select="substring-before(substring-after(//pmd/@timestamp, 'T'), '.')" />
	</xsl:template>

	<xsl:template
		match="mbviolations">
		<html>
			<head>
				<title>
					Multi Browser violations report
				</title>
				<script
					type="text/javascript"
					src="sorttable.js"></script>
				<style
					type="text/css">
					body { margin-left: 2%; margin-right: 2%; font:normal verdana,arial,helvetica;
					color:#000000; }
					table { border-collapse : collapse;}
					table.sortable tr th { padding-left:
					3px;font-weight: bold;
					text-align:left;font-size:11px;color:#5E5E5E;font-family:tahoma;border-bottom: 1px solid
					#B3B3B2;border-top: 1px solid #B3B3B2;background-color:#E6E7E3;}
					table.sortable tr td.first {
					padding-left: 3px;font-weight: normal;
					text-align:left;font-size:11px;color:#5E5E5E;font-family:tahoma;border-bottom: 1px solid
					#D3D3D3;border-top: 1px solid #D3D3D3;background-color:#F5F6FD; }
					table.sortable tr td.second {
					padding-left: 3px;font-weight: normal;
					text-align:left;font-size:11px;color:#5E5E5E;font-family:tahoma;border-bottom: 1px solid
					#D3D3D3;border-top: 1px solid #D3D3D3;background-color:#FFFFFF; }
					table.sortable tr td.third {
					padding-left: 3px;font-weight: normal;
					text-align:left;font-size:11px;color:#5E5E5E;font-family:tahoma;border-bottom: 1px solid
					#D3D3D3;border-top: 1px solid #D3D3D3;background-color:#F9F9F9; }

					.p1 { color:#FF0000; }
					.p2 {
					color:#FFA200; }
					.p3 { color:#D71C8E; }
					.p4 { color:#0A77CB; }
					a
					{
					color :
					#0000FF;
					}

					div.left{font-size:11px;color:#5E5E5E;font-family:tahoma;font-weight:normal;}
					div.leftBorder{height:15px;font-size:11px;color:#5E5E5E;font-family:tahoma;font-weight:normal;border-bottom:
					1px solid #B3B3B2;}
					div.leftSummary{padding-top:5px;height:20px;font-size:11px;color:#5E5E5E;font-family:tahoma;font-weight:normal;border-bottom:
					0px solid #B3B3B2;}
		
    </style>
			</head>
			<body>
				<div
					class="left">
					Report Name:
					<b>Static Analysis Report with Cordys Multi Browser Violations Detector</b>
				</div>
				<div
					class="leftSummary">
					<b>Summary</b>
					[Files:
					<xsl:value-of
						select="count(//file)" />
					&#xa0;&#xa0;&#xa0;Total Violations:
					<xsl:value-of
						select="count(//violation)" />
					&#xa0;&#xa0;&#xa0;MBviolation Priority1:
					<font
						color="#FF0000">
						<xsl:value-of
							select="count(//violation[@priority = 1 and @Type='MBviolation'])" />
					</font>
					&#xa0;&#xa0;&#xa0;MBviolation Priority2:
					<font
						color="#FFA200">
						<xsl:value-of
							select="count(//violation[@priority = 2 and @Type='MBviolation'])" />
					</font>
					&#xa0;&#xa0;&#xa0;SCASviolation Priority1:
					<font
						color="#FF0000">
						<xsl:value-of
							select="count(//violation[@priority = 1 and @Type='SCASviolation'])" />
					</font>
					&#xa0;&#xa0;&#xa0;SCASviolation Priority2:
					<font
						color="#FFA200">
						<xsl:value-of
							select="count(//violation[@priority = 2 and @Type='SCASviolation'])" />
					</font>
					&#xa0;&#xa0;&#xa0;SCASviolation Priority3:
					<font
						color="#D71C8E">
						<xsl:value-of
							select="count(//violation[@priority = 3 and @Type='SCASviolation'])" />
					</font>
					&#xa0;&#xa0;&#xa0;SCASviolation Priority4:
					<font
						color="#0A77CB">
						<xsl:value-of
							select="count(//violation[@priority = 4 and @Type='SCASviolation'])" />
					</font>
					&#xa0;&#xa0;&#xa0;Formatviolation Priority1:
					<font
						color="#FF0000">
						<xsl:value-of
							select="count(//violation[@priority = 1 and @Type='Formatviolation'])" />
					</font>
					&#xa0;&#xa0;&#xa0;Formatviolation Priority2:
					<font
						color="#FFA200">
						<xsl:value-of
							select="count(//violation[@priority = 2 and @Type='Formatviolation'])" />
					</font>
					&#xa0;&#xa0;&#xa0;Un_Matched_NOMBV:
					<font
						color="#FF0000">
						<xsl:value-of
							select="count(//violation/description[text()='#violations != #NOMBV'])" />
					</font>
					&#xa0;&#xa0;&#xa0;i18nViolation Priority1:
					<font
						color="#FF0000">
						<xsl:value-of
							select="count(//violation[@priority = 1 and @Type='i18nViolation'])" />
					</font>
					]
				</div>

				<xsl:for-each
					select="folder">
					<div>
						<h1 style="font-size:20px;color:#5E5E5E;font-family:verdana,arial,helvetica;">
							<xsl:value-of
								disable-output-escaping="yes"
								select="@name" />
						</h1>
					</div>
					<table
						border="0"
						width="100%"
						class="sortable"
						id="sortable_id">
						<tr>
							<th
								width="5%">Priority</th>
							<th
								width="60%">File Name</th>
							<th
								width="10%">File Type</th>
							<th
								width="10%">Violation Type</th>
							<th
								width="5%">Line#</th>
							<th
								width="15%">Violation</th>

						</tr>

						<xsl:for-each
							select="file">
							<xsl:variable
								name="filename"
								select="@name" />
							<xsl:variable
								name="filetype"
								select="@type" />
							<xsl:for-each
								select="violation[@priority = 1]">
								<tr>
									<xsl:attribute name="title">
										<xsl:value-of disable-output-escaping="yes" select="linecontent" />
									</xsl:attribute>
									<td
										class="first">
										<div>
											<xsl:attribute
												name="class"><xsl:call-template
												name="priorityDiv" /></xsl:attribute>
											<xsl:value-of
												disable-output-escaping="yes"
												select="@priority" />
										</div>
									</td>
									<td
										class="first">
										<xsl:value-of
											disable-output-escaping="yes"
											select="$filename" />
									</td>
									<td
										class="second">
										<xsl:value-of
											disable-output-escaping="yes"
											select="$filetype" />
									</td>
									<td
										class="second">
										<xsl:value-of
											disable-output-escaping="yes"
											select="@Type" />
									</td>
									<td
										class="second">
										<xsl:value-of
											disable-output-escaping="yes"
											select="@line" />
									</td>
									<td
										class="third">
										<xsl:if
											test="@ref">
											<a>
												<xsl:attribute
													name="href"><xsl:value-of
													select="@ref" /></xsl:attribute>
												<xsl:call-template
													name="message" />
											</a>
										</xsl:if>
										<xsl:if
											test="not(@ref)">
											<xsl:call-template
												name="message" />
										</xsl:if>
									</td>
								</tr>
							</xsl:for-each>
						</xsl:for-each>
						<xsl:for-each
							select="file">
							<xsl:variable
								name="filename"
								select="@name" />
							<xsl:variable
								name="filetype"
								select="@type" />
							<xsl:for-each
								select="violation[@priority = 2]">
								<tr>
									<xsl:attribute name="title">
										<xsl:value-of disable-output-escaping="yes" select="linecontent" />
									</xsl:attribute>
									<td
										class="first">
										<div>
											<xsl:attribute
												name="class"><xsl:call-template
												name="priorityDiv" /></xsl:attribute>
											<xsl:value-of
												disable-output-escaping="yes"
												select="@priority" />
										</div>
									</td>
									<td
										class="first">
										<xsl:value-of
											disable-output-escaping="yes"
											select="$filename" />
									</td>
									<td
										class="second">
										<xsl:value-of
											disable-output-escaping="yes"
											select="$filetype" />
									</td>
									<td
										class="second">
										<xsl:value-of
											disable-output-escaping="yes"
											select="@Type" />
									</td>
									<td
										class="second">
										<xsl:value-of
											disable-output-escaping="yes"
											select="@line" />
									</td>
									<td
										class="third">
										<xsl:if
											test="@ref">
											<a>
												<xsl:attribute
													name="href"><xsl:value-of
													select="@ref" /></xsl:attribute>
												<xsl:call-template
													name="message" />
											</a>
										</xsl:if>
										<xsl:if
											test="not(@ref)">
											<xsl:call-template
												name="message" />
										</xsl:if>
									</td>
								</tr>
							</xsl:for-each>
						</xsl:for-each>
						<xsl:for-each
							select="file">
							<xsl:variable
								name="filename"
								select="@name" />
							<xsl:variable
								name="filetype"
								select="@type" />
							<xsl:for-each
								select="violation[@priority = 3]">
								<tr>
									<xsl:attribute name="title">
										<xsl:value-of disable-output-escaping="yes" select="linecontent" />
									</xsl:attribute>
									<td
										class="first">
										<div>
											<xsl:attribute
												name="class"><xsl:call-template
												name="priorityDiv" /></xsl:attribute>
											<xsl:value-of
												disable-output-escaping="yes"
												select="@priority" />
										</div>
									</td>
									<td
										class="first">
										<xsl:value-of
											disable-output-escaping="yes"
											select="$filename" />
									</td>
									<td
										class="second">
										<xsl:value-of
											disable-output-escaping="yes"
											select="$filetype" />
									</td>
									<td
										class="second">
										<xsl:value-of
											disable-output-escaping="yes"
											select="@Type" />
									</td>
									<td
										class="second">
										<xsl:value-of
											disable-output-escaping="yes"
											select="@line" />
									</td>
									<td
										class="third">
										<xsl:if
											test="@ref">
											<a>
												<xsl:attribute
													name="href"><xsl:value-of
													select="@ref" /></xsl:attribute>
												<xsl:call-template
													name="message" />
											</a>
										</xsl:if>
										<xsl:if
											test="not(@ref)">
											<xsl:call-template
												name="message" />
										</xsl:if>
									</td>
								</tr>
							</xsl:for-each>
						</xsl:for-each>
						<xsl:for-each
							select="file">
							<xsl:variable
								name="filename"
								select="@name" />
							<xsl:variable
								name="filetype"
								select="@type" />
							<xsl:for-each
								select="violation[@priority = 4]">
								<tr>
									<xsl:attribute name="title">
										<xsl:value-of disable-output-escaping="yes" select="linecontent" />
									</xsl:attribute>
									<td
										class="first">
										<div>
											<xsl:attribute
												name="class"><xsl:call-template
												name="priorityDiv" /></xsl:attribute>
											<xsl:value-of
												disable-output-escaping="yes"
												select="@priority" />
										</div>
									</td>
									<td
										class="first">
										<xsl:value-of
											disable-output-escaping="yes"
											select="$filename" />
									</td>
									<td
										class="second">
										<xsl:value-of
											disable-output-escaping="yes"
											select="$filetype" />
									</td>
									<td
										class="second">
										<xsl:value-of
											disable-output-escaping="yes"
											select="@Type" />
									</td>
									<td
										class="second">
										<xsl:value-of
											disable-output-escaping="yes"
											select="@line" />
									</td>
									<td
										class="third">
										<xsl:if
											test="@ref">
											<a>
												<xsl:attribute
													name="href"><xsl:value-of
													select="@ref" /></xsl:attribute>
												<xsl:call-template
													name="message" />
											</a>
										</xsl:if>
										<xsl:if
											test="not(@ref)">
											<xsl:call-template
												name="message" />
										</xsl:if>
									</td>
								</tr>
							</xsl:for-each>
						</xsl:for-each>
						
					</table>
				</xsl:for-each>

			</body>
		</html>
	</xsl:template>

</xsl:stylesheet>
